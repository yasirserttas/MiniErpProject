package com.yasir.erp.minierp.modules.purchaseOrder.application.exception;

import com.yasir.erp.minierp.common.exception.BusinessException;

public class PurchaseOrderNotFoundException extends BusinessException {
    public PurchaseOrderNotFoundException(String purchaseOrderId) {
        super("PURCHASE_ORDER_NOT_FOUND" ,String.format("PurchaseOrder not found. ID[%s]",purchaseOrderId));
    }
}
