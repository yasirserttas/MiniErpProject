package com.yasir.erp.minierp.modules.orderItem.domain.port.inbound.command;

public interface DeleteOrderItemUseCase {
    void deleteOrderItem(String orderItemId);
}