package com.yasir.erp.minierp.modules.order.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.order.application.dto.OrderDto;
import com.yasir.erp.minierp.modules.order.domain.model.OrderStatus;

public interface FindOrderByStatusUseCase {
    OrderDto findDtoByOrderStatusAndActive(OrderStatus status, boolean active);
}