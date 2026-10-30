terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "6.69.1"
    }
  }

  backend "s3" {
    bucket = "repo-status-tracker-1853"
    key = "terraform_event_processing.tfstate"
    region = "eu-north-1"
    use_lockfile = true
  }
} 

provider "aws" {
  region = "eu-north-1"
}
