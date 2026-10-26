data "aws_caller_identity" "current" {}
data "aws_region" "current" {}
#
resource "aws_apigatewayv2_api" "http_api" {
  name          = "repo-status-tracker-api-1853"
  protocol_type = "HTTP"
}

resource "aws_apigatewayv2_integration" "lambda_integration" {
  api_id                = aws_apigatewayv2_api.http_api.id
  integration_type      = "AWS_PROXY"
  description           = "Integration with Backend Lambda"
  integration_method    = "POST"
  integration_uri       = "arn:aws:lambda:${data.aws_region.current.region}:${data.aws_caller_identity.current.account_id}:function:repo-registrar"
}

resource "aws_apigatewayv2_route" "health" {
  api_id    = aws_apigatewayv2_api.http_api.id
  route_key = "GET /api/v1/ping"
  target = "integrations/${aws_apigatewayv2_integration.lambda_integration.id}"
}

resource "aws_apigatewayv2_route" "register-repo" {
  api_id    = aws_apigatewayv2_api.http_api.id
  route_key = "POST /api/v1/registration"
  target = "integrations/${aws_apigatewayv2_integration.lambda_integration.id}"
}

resource "aws_apigatewayv2_stage" "dev" {
  api_id      = aws_apigatewayv2_api.http_api.id
  name        = "dev"
  auto_deploy = true
}
