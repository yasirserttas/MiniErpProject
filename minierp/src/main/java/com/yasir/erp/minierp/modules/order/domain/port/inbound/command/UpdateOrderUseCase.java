package com.yasir.erp.minierp.modules.order.domain.port.inbound.command;

import com.yasir.erp.minierp.modules.order.application.dto.OrderDto;
import com.yasir.erp.minierp.modules.order.application.dto.request.UpdateOrderDtoRequest;

public interface UpdateOrderUseCase {
    OrderDto updateOrder(UpdateOrderDtoRequest req);
}
