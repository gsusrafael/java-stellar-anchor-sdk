package org.stellar.anchor.platform.rpc;

import static java.util.Collections.emptySet;
import static org.stellar.anchor.api.platform.PlatformTransactionData.Kind.WITHDRAWAL;
import static org.stellar.anchor.api.rpc.method.RpcMethod.NOTIFY_AMOUNTS_UPDATED;
import static org.stellar.anchor.api.sep.SepTransactionStatus.*;

import java.util.Set;
import org.stellar.anchor.api.exception.BadRequestException;
import org.stellar.anchor.api.exception.rpc.InvalidParamsException;
import org.stellar.anchor.api.exception.rpc.InvalidRequestException;
import org.stellar.anchor.api.platform.PlatformTransactionData.Kind;
import org.stellar.anchor.api.platform.PlatformTransactionData.Sep;
import org.stellar.anchor.api.rpc.method.AmountAssetRequest;
import org.stellar.anchor.api.rpc.method.NotifyAmountsUpdatedRequest;
import org.stellar.anchor.api.rpc.method.RpcMethod;
import org.stellar.anchor.api.sep.SepTransactionStatus;
import org.stellar.anchor.asset.AssetService;
import org.stellar.anchor.event.EventService;
import org.stellar.anchor.metrics.MetricsService;
import org.stellar.anchor.platform.data.JdbcSep24Transaction;
import org.stellar.anchor.platform.data.JdbcSepTransaction;
import org.stellar.anchor.platform.utils.AssetValidationUtils;
import org.stellar.anchor.platform.validator.RequestValidator;
import org.stellar.anchor.sep24.Sep24TransactionStore;
import org.stellar.anchor.sep31.Sep31TransactionStore;
import org.stellar.anchor.sep6.Sep6TransactionStore;

public class NotifyAmountsUpdatedHandler extends RpcMethodHandler<NotifyAmountsUpdatedRequest> {

  public NotifyAmountsUpdatedHandler(
      Sep6TransactionStore txn6Store,
      Sep24TransactionStore txn24Store,
      Sep31TransactionStore txn31Store,
      RequestValidator requestValidator,
      AssetService assetService,
      EventService eventService,
      MetricsService metricsService) {
    super(
        txn6Store,
        txn24Store,
        txn31Store,
        requestValidator,
        assetService,
        eventService,
        metricsService,
        NotifyAmountsUpdatedRequest.class);
  }

  @Override
  protected void validate(JdbcSepTransaction txn, NotifyAmountsUpdatedRequest request)
      throws InvalidParamsException, InvalidRequestException, BadRequestException {
    super.validate(txn, request);

    AssetValidationUtils.validateAsset(
        "amount_out",
        AmountAssetRequest.builder()
            .amount(request.getAmountOut().getAmount())
            .asset(txn.getAmountOutAsset())
            .build(),
        assetService);
    AssetValidationUtils.validateAsset(
        "amount_fee",
        AmountAssetRequest.builder()
            .amount(request.getAmountFee().getAmount())
            .asset(txn.getAmountFeeAsset())
            .build(),
        true,
        assetService);
  }

  @Override
  public RpcMethod getRpcMethod() {
    return NOTIFY_AMOUNTS_UPDATED;
  }

  @Override
  protected SepTransactionStatus getNextStatus(
      JdbcSepTransaction txn, NotifyAmountsUpdatedRequest request) {
    return PENDING_ANCHOR;
  }

  @Override
  protected Set<SepTransactionStatus> getSupportedStatuses(JdbcSepTransaction txn) {
    switch (Sep.from(txn.getProtocol())) {
      case SEP_6:
        return Set.of(INCOMPLETE, PENDING_ANCHOR, PENDING_CUSTOMER_INFO_UPDATE);
      case SEP_24:
        JdbcSep24Transaction txn24 = (JdbcSep24Transaction) txn;
        if (WITHDRAWAL == Kind.from(txn24.getKind())) {
          if (areFundsReceived(txn24)) {
            return Set.of(PENDING_ANCHOR);
          }
        }
        return emptySet();
      default:
        return emptySet();
    }
  }

  @Override
  protected void updateTransactionWithRpcRequest(
      JdbcSepTransaction txn, NotifyAmountsUpdatedRequest request) throws InvalidParamsException {
    txn.setAmountOut(request.getAmountOut().getAmount());
    txn.setAmountFee(request.getAmountFee().getAmount());
  }
}
