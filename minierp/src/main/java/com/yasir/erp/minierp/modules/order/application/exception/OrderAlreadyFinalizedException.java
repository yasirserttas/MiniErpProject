package com.yasir.erp.minierp.modules.order.application.exception;

public class OrderAlreadyFinalizedException extends RuntimeException{

    public OrderAlreadyFinalizedException(String orderId) {
        super("The order cannot be updated because it has already been finalized. Order ID: " + orderId);
    }
}
