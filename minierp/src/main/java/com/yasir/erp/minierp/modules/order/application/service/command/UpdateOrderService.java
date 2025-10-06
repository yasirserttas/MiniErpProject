package com.yasir.erp.minierp.modules.order.application.service.command;

import com.yasir.erp.minierp.modules.order.domain.port.inbound.command.UpdateOrderUseCase;
import com.yasir.erp.minierp.modules.address.application.exception.AddressNotFoundException;
import com.yasir.erp.minierp.modules.order.application.exception.OrderAlreadyFinalizedException;
import com.yasir.erp.minierp.modules.order.application.exception.OrderNotFoundException;
import com.yasir.erp.minierp.modules.address.domain.model.Address;
import com.yasir.erp.minierp.modules.address.domain.port.outbound.query.AddressQueryPort;
import com.yasir.erp.minierp.modules.order.application.converter.OrderConverter;
import com.yasir.erp.minierp.modules.order.application.dto.OrderDto;
import com.yasir.erp.minierp.modules.order.application.dto.request.UpdateOrderDtoRequest;
import com.yasir.erp.minierp.modules.order.domain.model.Order;
import com.yasir.erp.minierp.modules.order.domain.model.OrderStatus;
import com.yasir.erp.minierp.modules.order.domain.port.outbound.command.OrderCommandPort;
import com.yasir.erp.minierp.modules.order.domain.port.outbound.query.OrderActiveQueryPort;
import com.yasir.erp.minierp.modules.orderItem.domain.model.OrderItem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class UpdateOrderService implements UpdateOrderUseCase {

    private final OrderActiveQueryPort orderActiveQueryPort;
    private final OrderCommandPort orderCommandPort;
    private final AddressQueryPort addressQueryPort;
    private final OrderConverter orderConverter;

    public UpdateOrderService(OrderActiveQueryPort orderActiveQueryPort,
                              OrderCommandPort orderCommandPort,
                              AddressQueryPort addressQueryPort,
                              OrderConverter orderConverter) {
        this.orderActiveQueryPort = orderActiveQueryPort;
        this.orderCommandPort = orderCommandPort;
        this.addressQueryPort = addressQueryPort;
        this.orderConverter = orderConverter;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public OrderDto updateOrder(UpdateOrderDtoRequest req) {
        Order existing = orderActiveQueryPort.findByIdAndActive(req.getId(),true)
                .orElseThrow(() -> new OrderNotFoundException(req.getId()));

        if (!OrderStatus.PENDING.equals(existing.getOrderStatus())) {
            throw new OrderAlreadyFinalizedException(existing.getId());
        }

        Address shipping = addressQueryPort.findById(req.getShippingAddressId())
                .orElseThrow(() -> new AddressNotFoundException(req.getShippingAddressId()));
        Address billing = addressQueryPort.findById(req.getBillingAddressId())
                .orElseThrow(() -> new AddressNotFoundException(req.getBillingAddressId()));

        BigDecimal total = existing.getOrderItems().stream()
                .map(OrderItem::getTotalPriceWithTax)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order updated = new Order(
                existing.getId(), existing.getCustomer(), existing.getUser(),
                existing.getOrderItems(), existing.getOrderStatus(),
                existing.getCreatedAt(), LocalDateTime.now(),
                shipping, billing, existing.getOrderDate(), total,
                req.getNote(), true
        );

        return orderConverter.convertToDto(orderCommandPort.save(updated));
    }
}
