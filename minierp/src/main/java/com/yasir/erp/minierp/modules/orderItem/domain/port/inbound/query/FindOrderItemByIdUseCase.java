package com.yasir.erp.minierp.modules.orderItem.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.orderItem.application.dto.OrderItemDto;

public interface FindOrderItemByIdUseCase {
    OrderItemDto findOrderItemDtoById(String orderItemId);
}