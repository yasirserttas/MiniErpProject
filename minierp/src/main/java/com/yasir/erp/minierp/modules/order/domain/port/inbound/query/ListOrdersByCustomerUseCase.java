package com.yasir.erp.minierp.modules.order.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.order.application.dto.OrderDto;
import java.util.Set;
import java.util.UUID;

public interface ListOrdersByCustomerUseCase {
    Set<OrderDto> findAllDtoByCustomerIdAndActive(UUID customerId, boolean active);
}