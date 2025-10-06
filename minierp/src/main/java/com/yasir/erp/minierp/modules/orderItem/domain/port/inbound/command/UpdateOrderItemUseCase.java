package com.yasir.erp.minierp.modules.orderItem.domain.port.inbound.command;

import com.yasir.erp.minierp.modules.orderItem.application.dto.OrderItemDto;
import com.yasir.erp.minierp.modules.orderItem.application.dto.request.UpdateOrderItemDtoRequest;

public interface UpdateOrderItemUseCase {
    OrderItemDto updateOrderItem(UpdateOrderItemDtoRequest req);
}