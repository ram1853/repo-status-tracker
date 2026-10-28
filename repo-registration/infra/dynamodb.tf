resource "aws_dynamodb_table" "registration-detail-table" {
  name           = "RegistrationDetail"
  billing_mode   = "PAY_PER_REQUEST"
  hash_key       = "owner"
  range_key      = "repo"

  attribute {
    name = "owner"
    type = "S"
  }

  attribute {
    name = "repo"
    type = "S"
  }
}
