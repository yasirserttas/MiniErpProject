package com.yasir.erp.minierp.modules.order.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.order.application.dto.OrderDto;
import com.yasir.erp.minierp.modules.order.domain.model.OrderStatus;
import java.util.Set;

public interface ListOrdersByStatusUseCase {
    Set<OrderDto> findAllDtoByOrderStatusAndActive(OrderStatus status, boolean active);
}