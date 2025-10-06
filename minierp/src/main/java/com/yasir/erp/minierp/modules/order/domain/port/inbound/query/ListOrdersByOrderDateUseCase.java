package com.yasir.erp.minierp.modules.order.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.order.application.dto.OrderDto;
import java.time.LocalDateTime;
import java.util.Set;

public interface ListOrdersByOrderDateUseCase {
    Set<OrderDto> findAllDtoByOrderDateBetweenAndActive
        (LocalDateTime start, LocalDateTime end, boolean active);
}