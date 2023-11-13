terraform {
  cloud {
    organization = "gsusrafael"

    workspaces {
      name = "java-stellar-anchor"
    }
  }
}

variable "environment" {
  description = "deployment environment"
  type = string
  default = "dev"
}

variable "hosted_zone_name" {
  description = "name of hosted zone for anchor platform"
  type = string
}

variable "jwt_secret" {
  type  = string
  default = "YA4gPKwQGijjWWDgEUReacoQAWrrsU0G8QgGGKRKvX30g3Uu"
}
variable "sep10_signing_seed" {
    type  = string
}

variable "postgresql_username" {
    type  = string
    default = "dev_stellar"
}
variable "postgresql_password" {
    type  = string
    default = "ZGV2X3N0ZWxsYXIK"
}

variable  "sqs_access_key" {
  type = string
}

variable  "sqs_secret_key" {
  type = string
}

variable  "platform_to_anchor_secret" {
  type = string
}

variable  "anchor_to_platform_secret" {
  type = string
}

variable "anchor_config_build_spec" {
  type = string
  default = "docs/resources/deployment-examples/example-fargate/buildspec-dev.yml"
}

variable "codebuild_source_version" {
  description = "deployment environment"
  type = string
  default = "main"
}  

variable "anchor_config_repository" {
  type = string
  default = "https://github.com/reecexlm/java-stellar-anchor-sdk"
}

variable "aws_account" {
  type = string
}

variable "aws_region" {
  type = string
}

variable "image_tag" {
  type = string
  default = "latest"
}
