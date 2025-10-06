package com.yasir.erp.minierp.modules.orderItem.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.orderItem.application.dto.OrderItemDto;
import java.util.Set;

public interface ListOrderItemsByOrderUseCase {
    Set<OrderItemDto> listOrderItemSetDtoByOrderId(String orderId);
}