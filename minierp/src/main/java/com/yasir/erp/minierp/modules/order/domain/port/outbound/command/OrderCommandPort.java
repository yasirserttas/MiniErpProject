package com.yasir.erp.minierp.modules.order.domain.port.outbound.command;

import com.yasir.erp.minierp.modules.order.domain.model.Order;

public interface OrderCommandPort {
    Order save(Order order);
}
