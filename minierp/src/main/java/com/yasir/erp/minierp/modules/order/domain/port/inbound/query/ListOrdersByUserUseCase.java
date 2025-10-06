package com.yasir.erp.minierp.modules.order.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.order.application.dto.OrderDto;
import java.util.Set;
import java.util.UUID;

public interface ListOrdersByUserUseCase {
    Set<OrderDto> findAllDtoByUserIdAndActive(UUID userId, boolean active);
}