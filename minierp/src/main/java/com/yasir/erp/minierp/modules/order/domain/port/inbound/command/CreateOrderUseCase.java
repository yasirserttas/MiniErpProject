package com.yasir.erp.minierp.modules.order.domain.port.inbound.command;

import com.yasir.erp.minierp.modules.order.application.dto.OrderDto;
import com.yasir.erp.minierp.modules.order.application.dto.request.CreateOrderDtoRequest;

public interface CreateOrderUseCase {
    OrderDto addOrder(CreateOrderDtoRequest createOrderDtoRequest);
}