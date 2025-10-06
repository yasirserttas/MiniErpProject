package com.yasir.erp.minierp.modules.order.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.order.application.dto.OrderDto;

public interface FindOrderByIdUseCase {
    OrderDto findDtoByIdAndActive(String id, boolean active);
}