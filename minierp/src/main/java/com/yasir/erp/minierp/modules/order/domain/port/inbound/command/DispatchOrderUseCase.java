package com.yasir.erp.minierp.modules.order.domain.port.inbound.command;

import com.yasir.erp.minierp.modules.order.application.dto.OrderDto;
import com.yasir.erp.minierp.dto.dispatchNote.request.CreateOrderDispatchedDispatchOrder;

public interface DispatchOrderUseCase {
    OrderDto dispatchOrder(String orderId, CreateOrderDispatchedDispatchOrder req);
}