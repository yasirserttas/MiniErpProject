package com.yasir.erp.minierp.modules.order.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.order.application.dto.OrderDto;
import java.util.UUID;

public interface FindOrderByCustomerUseCase {
    OrderDto findDtoByCustomerIdAndActive(UUID customerId, boolean active);
}