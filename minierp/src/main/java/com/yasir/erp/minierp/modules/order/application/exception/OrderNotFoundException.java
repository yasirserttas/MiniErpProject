package com.yasir.erp.minierp.modules.order.application.exception;

import com.yasir.erp.minierp.common.exception.BusinessException;

public class OrderNotFoundException extends BusinessException {
    public OrderNotFoundException(String orderId) {
        super("ORDER_NOT_FOUND",String.format("Order not found. ID[%s]",orderId));
    }
}
