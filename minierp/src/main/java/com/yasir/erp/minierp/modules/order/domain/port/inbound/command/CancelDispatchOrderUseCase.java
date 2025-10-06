package com.yasir.erp.minierp.modules.order.domain.port.inbound.command;

import com.yasir.erp.minierp.modules.order.application.dto.OrderDto;

public interface CancelDispatchOrderUseCase {
    OrderDto cancelDispatchOrder(String orderId);
}