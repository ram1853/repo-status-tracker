variable "function_name" {
  type    = string
  default = "event-processing"
}

variable "zip_tag" {
  type = string
}
#
data "aws_caller_identity" "current" {}
data "aws_region" "current" {}
data "aws_partition" "current" {}

data "terraform_remote_state" "common" {
    backend = "s3"
    config = {
        bucket = "repo-status-tracker-1853"
        key    = "terraform_common.tfstate"
        region = "eu-north-1"
    }
}

data "aws_iam_policy_document" "assume_role" {
    statement {
      effect = "Allow"

      principals {
        type        = "Service"
        identifiers = ["lambda.amazonaws.com"]
      }

      actions = ["sts:AssumeRole"]
    }
}

resource "aws_iam_policy" "cloudwatch-policy" {
  name = "event-processing-cloudwatch-policy"
  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
        {
            Action  = ["logs:CreateLogStream", "logs:PutLogEvents"]
            Effect   = "Allow"
            Resource = "arn:aws:logs:${data.aws_region.current.region}:${data.aws_caller_identity.current.account_id}:log-group:/aws/lambda/${var.function_name}:*"
        },
        {
            Action  = ["logs:CreateLogGroup"]
            Effect   = "Allow"
            Resource = "arn:aws:logs:${data.aws_region.current.region}:${data.aws_caller_identity.current.account_id}:*"
        }
    ]
  })
}

resource "aws_lambda_permission" "apigw_lambda" {
  statement_id  = "AllowExecutionFromAPIGateway"
  action        = "lambda:InvokeFunction"
  function_name = var.function_name
  principal     = "apigateway.amazonaws.com"
  source_arn    = "${data.terraform_remote_state.common.outputs.api_gateway_execution_arn}/*/*"
}

# IAM Role that will be assumed by Lambda
resource "aws_iam_role" "event-processing-role" {
  name               = "event-processing-role"
  assume_role_policy = data.aws_iam_policy_document.assume_role.json
}

resource "aws_iam_role_policy_attachment" "attach-cloudwatch-policy" {
  role       = aws_iam_role.event-processing-role.name
  policy_arn = aws_iam_policy.cloudwatch-policy.arn
}

resource "aws_lambda_function" "event-processing" {
  function_name     = var.function_name
  role              = aws_iam_role.event-processing-role.arn
  timeout           = 10
  memory_size       = 512 
  package_type      = "Zip"
  s3_bucket         = "repo-status-tracker-1853"
  s3_key            = "lambda/event-processing-${var.zip_tag}.zip"
  runtime           = "provided.al2023"
  handler           = "bootstrap" 
  architectures     = ["x86_64"]

  layers = [
      "arn:aws:lambda:${data.aws_region.current.region}:753240598075:layer:LambdaAdapterLayerX86:24"
    ]

  environment {
    variables = {
      AWS_LWA_READINESS_CHECK_PATH = "/api/v1/event/ping"
      AWS_LWA_REMOVE_BASE_PATH     = "/dev"
    }
  }
}