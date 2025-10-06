package com.yasir.erp.minierp.modules.order.domain.port.inbound.command;

import com.yasir.erp.minierp.modules.order.application.dto.OrderDto;
import com.yasir.erp.minierp.modules.orderItem.application.dto.request.CreateOrderItemDtoRequest;

public interface AddItemToOrderUseCase {
    OrderDto addItemToOrder(String orderId, CreateOrderItemDtoRequest item);
}