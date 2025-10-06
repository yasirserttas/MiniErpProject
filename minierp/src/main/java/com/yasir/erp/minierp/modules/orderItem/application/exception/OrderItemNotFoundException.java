package com.yasir.erp.minierp.modules.orderItem.application.exception;

import com.yasir.erp.minierp.common.exception.BusinessException;

public class OrderItemNotFoundException extends BusinessException {

    public OrderItemNotFoundException(String orderItemId) {
        super("ORDER_ITEM_NOT_FOUND",String.format("OrderItem not found. ID[%s]",orderItemId));
    }
}
