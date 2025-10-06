package com.yasir.erp.minierp.modules.orderItem.application.service.query;

import com.yasir.erp.minierp.modules.orderItem.application.exception.OrderItemNotFoundException;
import com.yasir.erp.minierp.modules.orderItem.application.converter.OrderItemConverter;
import com.yasir.erp.minierp.modules.orderItem.application.dto.OrderItemDto;
import com.yasir.erp.minierp.modules.orderItem.domain.model.OrderItem;
import com.yasir.erp.minierp.modules.orderItem.domain.port.inbound.query.FindOrderItemByIdUseCase;
import com.yasir.erp.minierp.modules.orderItem.domain.port.inbound.query.ListOrderItemsByOrderUseCase;
import com.yasir.erp.minierp.modules.orderItem.domain.port.outbound.query.OrderItemQueryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class OrderItemQueryService implements
        FindOrderItemByIdUseCase,
        ListOrderItemsByOrderUseCase {

    private final OrderItemQueryPort orderItemQueryPort;
    private final OrderItemConverter orderItemConverter;

    public OrderItemQueryService(OrderItemQueryPort orderItemQueryPort,
                                 OrderItemConverter orderItemConverter) {
        this.orderItemQueryPort = orderItemQueryPort;
        this.orderItemConverter = orderItemConverter;
    }


    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public OrderItemDto findOrderItemDtoById(String orderItemId) { // FindOrderItemByIdUseCase
        return orderItemConverter.convertToOrderItemDto(findById(orderItemId));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<OrderItemDto> listOrderItemSetDtoByOrderId(String orderId) { // ListOrderItemsByOrderUseCase -> method adını böyle tanımladıysan
        return orderItemConverter.convertToOrderItemDtoSet(findAllByOrderId(orderId));
    }


    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected OrderItem findById(String orderItemId) {
        return orderItemQueryPort.findById(orderItemId)
                .orElseThrow(() -> new OrderItemNotFoundException("OrderItem not found: " + orderItemId));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Set<OrderItem> findAllByOrderId(String orderId) {
        return orderItemQueryPort.findAllByOrderId(orderId);
    }
}
