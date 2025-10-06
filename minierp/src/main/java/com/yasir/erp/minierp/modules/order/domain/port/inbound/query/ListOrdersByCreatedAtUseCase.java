package com.yasir.erp.minierp.modules.order.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.order.application.dto.OrderDto;
import java.time.LocalDateTime;
import java.util.Set;

public interface ListOrdersByCreatedAtUseCase {
    Set<OrderDto> findAllDtoByCreatedAtBetweenAndActive
        (LocalDateTime start, LocalDateTime end, boolean active);
}