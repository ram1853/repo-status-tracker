variable "function_name" {
  type    = string
  default = "repo-registration"
}

variable "image_tag" {
  type = string
}

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
  name = "repo-registration-cloudwatch-policy"
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

resource "aws_iam_policy" "dynamodb-policy" {
  name = "repo-registration-dynamodb-policy"
  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
        {
            Action  = ["dynamodb:PutItem"]
            Effect   = "Allow"
            Resource = [aws_dynamodb_table.registration-detail-table.arn]
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
resource "aws_iam_role" "repo-registrar-role" {
  name               = "repo-registration-role"
  assume_role_policy = data.aws_iam_policy_document.assume_role.json
}

resource "aws_iam_role_policy_attachment" "attach-cloudwatch-policy" {
  role       = aws_iam_role.repo-registrar-role.name
  policy_arn = aws_iam_policy.cloudwatch-policy.arn
}

resource "aws_iam_role_policy_attachment" "attach-dynamodb-policy" {
  role       = aws_iam_role.repo-registrar-role.name
  policy_arn = aws_iam_policy.dynamodb-policy.arn
}

resource "aws_lambda_function" "repo-registrar" {
  function_name     = var.function_name
  role              = aws_iam_role.repo-registrar-role.arn
  timeout           = 10
  package_type      = "Image"
  image_uri         = "545009866715.dkr.ecr.eu-north-1.amazonaws.com/repo-registration:${var.image_tag}"
  architectures     = ["x86_64"]

  environment {
    variables = {
      AWS_LWA_READINESS_CHECK_PATH = "/api/v1/ping"
      AWS_LWA_REMOVE_BASE_PATH     = "/dev"
    }
  }
}