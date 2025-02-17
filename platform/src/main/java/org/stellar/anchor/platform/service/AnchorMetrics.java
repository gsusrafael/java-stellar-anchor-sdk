package org.stellar.anchor.platform.service;

public enum AnchorMetrics {
  SEP24_TRANSACTION("sep24.transaction"),
  SEP31_TRANSACTION("sep31.transaction"),
  SEP31_TRANSACTION_DB("sep31.transaction.db"),
  SEP24_TRANSACTION_DB("sep24.transaction.db"),
  PAYMENT_RECEIVED("payment.received"),
  PAYMENT_SENT("payment.sent"),
  LOGGER("logger"),

  PLATFORM_RPC_TRANSACTION("platform_server.rpc_transaction"),

  // Metric Tags
  TAG_SEP31_STATUS_PENDING_STELLAR("pending_stellar"),
  TAG_SEP31_STATUS_PENDING_CUSTOMER("pending_customer_info_update"),
  TAG_SEP31_STATUS_PENDING_SENDER("pending_sender"),
  TAG_SEP31_STATUS_PENDING_RECEIVER("pending_receiver"),
  TAG_SEP31_STATUS_PENDING_EXTERNAL("pending_external"),
  TAG_SEP31_STATUS_COMPLETED("completed"),
  TAG_SEP31_STATUS_ERROR("error");

  private final String name;

  AnchorMetrics(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return this.name;
  }
}
