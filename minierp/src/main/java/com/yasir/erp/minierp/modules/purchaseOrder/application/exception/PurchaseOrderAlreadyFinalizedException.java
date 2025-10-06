package com.yasir.erp.minierp.modules.purchaseOrder.application.exception;

public class PurchaseOrderAlreadyFinalizedException extends RuntimeException {

    public PurchaseOrderAlreadyFinalizedException(String orderId) {
        super("The purchase order cannot be updated because it has already been finalized. Order ID: " + orderId);
    }
}