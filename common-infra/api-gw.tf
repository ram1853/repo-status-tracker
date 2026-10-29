data "aws_caller_identity" "current" {}
data "aws_region" "current" {}

resource "aws_apigatewayv2_api" "http_api" {
  name          = "repo-status-tracker-api"
  protocol_type = "HTTP"
}

resource "aws_apigatewayv2_integration" "lambda_integration" {
  api_id                = aws_apigatewayv2_api.http_api.id
  integration_type      = "AWS_PROXY"
  description           = "Integration with Backend Registration Lambda"
  integration_method    = "POST"
  integration_uri       = "arn:aws:lambda:${data.aws_region.current.region}:${data.aws_caller_identity.current.account_id}:function:repo-registration"
}

resource "aws_apigatewayv2_integration" "event_lambda_integration" {
  api_id                = aws_apigatewayv2_api.http_api.id
  integration_type      = "AWS_PROXY"
  description           = "Integration with Backend Event Processing Lambda"
  integration_method    = "POST"
  integration_uri       = "arn:aws:lambda:${data.aws_region.current.region}:${data.aws_caller_identity.current.account_id}:function:event-processing"
}

resource "aws_apigatewayv2_route" "health" {
  api_id    = aws_apigatewayv2_api.http_api.id
  route_key = "GET /api/v1/registration/ping"
  target = "integrations/${aws_apigatewayv2_integration.lambda_integration.id}"
}

resource "aws_apigatewayv2_route" "register-repo" {
  api_id    = aws_apigatewayv2_api.http_api.id
  route_key = "POST /api/v1/registration/create"
  target = "integrations/${aws_apigatewayv2_integration.lambda_integration.id}"
}

resource "aws_apigatewayv2_route" "health-event" {
  api_id    = aws_apigatewayv2_api.http_api.id
  route_key = "POST /api/v1/event/ping"
  target = "integrations/${aws_apigatewayv2_integration.event_lambda_integration.id}"
}

resource "aws_apigatewayv2_route" "event-processing" {
  api_id    = aws_apigatewayv2_api.http_api.id
  route_key = "POST /api/v1/event/process"
  target = "integrations/${aws_apigatewayv2_integration.event_lambda_integration.id}"
}

resource "aws_apigatewayv2_stage" "dev" {
  api_id      = aws_apigatewayv2_api.http_api.id
  name        = "dev"
  auto_deploy = true
}
