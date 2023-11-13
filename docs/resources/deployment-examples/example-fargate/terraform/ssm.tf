
resource "aws_ssm_parameter" "jwt_secret" {
  name  = "/${var.environment}/anchorplatform/JWT_SECRET"
  type  = "SecureString"
  value = "${var.jwt_secret}"

    tags = {
    environment = "${var.environment}"
  }
}

data "aws_ssm_parameter" "jwt_secret" {
  name  = "/${var.environment}/anchorplatform/JWT_SECRET"
  depends_on = [aws_ssm_parameter.jwt_secret]
}

resource "aws_ssm_parameter" "sep10_signing_seed" {
  name  = "/${var.environment}/anchorplatform/SEP10_SIGNING_SEED"
  type  = "SecureString"
  value = "${var.sep10_signing_seed}"

    tags = {
    environment = "${var.environment}"
  }
}

data "aws_ssm_parameter" "sep10_signing_seed" {
  name  = "/${var.environment}/anchorplatform/SEP10_SIGNING_SEED"
  depends_on = [aws_ssm_parameter.sep10_signing_seed]
}

resource "aws_ssm_parameter" "postgresql_username" {
  name  = "/${var.environment}/anchorplatform/POSTGRESQL_USERNAME"
  type  = "SecureString"
  value = "${var.postgresql_username}"

    tags = {
    environment = "${var.environment}"
  }
}

data "aws_ssm_parameter" "postgresql_username" {
  name  = "/${var.environment}/anchorplatform/POSTGRESQL_USERNAME" 
  depends_on = [aws_ssm_parameter.postgresql_username]
}

resource "aws_ssm_parameter" "postgresql_password" {
  name  = "/${var.environment}/anchorplatform/POSTGRESQL_PASSWORD"
  type  = "SecureString"
  value = "${var.postgresql_password}"

    tags = {
    environment = "${var.environment}"
  }
}

data "aws_ssm_parameter" "postgresql_password" {
  name  = "/${var.environment}/anchorplatform/POSTGRESQL_PASSWORD" 
  depends_on = [aws_ssm_parameter.postgresql_password]
}


resource "aws_ssm_parameter" "sqs_access_key" {
  name  = "/${var.environment}/anchorplatform/SQS_ACCESS_KEY"
  type  = "SecureString"
  value = "${var.sqs_access_key}"

    tags = {
    environment = "${var.environment}"
  }
}

data "aws_ssm_parameter" "sqs_access_key" {
  name = "/${var.environment}/anchorplatform/SQS_ACCESS_KEY"
  depends_on = [aws_ssm_parameter.sqs_access_key]

}

resource "aws_ssm_parameter" "sqs_secret_key" {
  name  = "/${var.environment}/anchorplatform/SQS_SECRET_KEY"
  type  = "SecureString"
  value = "${var.sqs_secret_key}"

    tags = {
    environment = "${var.environment}"
  }
}

data "aws_ssm_parameter" "sqs_secret_key" {
  name  = "/${var.environment}/anchorplatform/SQS_SECRET_KEY" 
  depends_on = [aws_ssm_parameter.sqs_secret_key]

}

resource "aws_ssm_parameter" "platform_to_anchor_secret" {
  name  = "/${var.environment}/anchorplatform/PLATFORM_TO_ANCHOR_SECRET"
  type  = "SecureString"
  value = "${var.platform_to_anchor_secret}"

    tags = {
    environment = "${var.environment}"
  }
}

data "aws_ssm_parameter" "platform_to_anchor_secret" {
  name  = "/${var.environment}/anchorplatform/PLATFORM_TO_ANCHOR_SECRET" 
  depends_on = [aws_ssm_parameter.platform_to_anchor_secret]

}
resource "aws_ssm_parameter" "anchor_to_platform_secret" {
  name  = "/${var.environment}/anchorplatform/ANCHOR_TO_PLATFORM_SECRET"
  type  = "SecureString"
  value = "${var.anchor_to_platform_secret}"

    tags = {
    environment = "${var.environment}"
  }
}

data "aws_ssm_parameter" "anchor_to_platform_secret" {
  name  = "/${var.environment}/anchorplatform/ANCHOR_TO_PLATFORM_SECRET" 
  depends_on = [aws_ssm_parameter.anchor_to_platform_secret]

}







