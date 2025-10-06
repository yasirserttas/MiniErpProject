package com.yasir.erp.minierp.modules.order.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.order.application.dto.OrderDto;
import com.yasir.erp.minierp.modules.order.domain.model.OrderStatus;
import java.time.LocalDateTime;
import java.util.Set;

public interface ListOrdersByStatusAndOrderDateUseCase {
    Set<OrderDto> findAllDtoByOrderStatusAndOrderDateBetweenAndActive
            (OrderStatus status, LocalDateTime start, LocalDateTime end, boolean active);
}