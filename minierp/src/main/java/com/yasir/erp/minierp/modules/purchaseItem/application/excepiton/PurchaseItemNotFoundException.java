package com.yasir.erp.minierp.modules.purchaseItem.application.excepiton;

import com.yasir.erp.minierp.common.exception.BusinessException;

public class PurchaseItemNotFoundException extends BusinessException {

    public PurchaseItemNotFoundException(String purchaseItemId) {
        super("PURCHASE_ITEM_NOT_FOUND" ,String.format("Purchase not found. ID[%s]",purchaseItemId));
    }
}
