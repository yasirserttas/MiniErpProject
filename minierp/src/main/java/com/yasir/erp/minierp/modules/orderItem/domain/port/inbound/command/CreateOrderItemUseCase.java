package com.yasir.erp.minierp.modules.orderItem.domain.port.inbound.command;

import com.yasir.erp.minierp.modules.orderItem.application.dto.OrderItemDto;
import com.yasir.erp.minierp.modules.orderItem.application.dto.request.CreateOrderItemDtoRequest;

public interface CreateOrderItemUseCase {
    OrderItemDto createOrderItem(CreateOrderItemDtoRequest req);
}