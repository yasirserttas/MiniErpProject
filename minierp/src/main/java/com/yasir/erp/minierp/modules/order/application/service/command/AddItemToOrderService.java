package com.yasir.erp.minierp.modules.order.application.service.command;

import com.yasir.erp.minierp.modules.order.application.exception.OrderAlreadyFinalizedException;
import com.yasir.erp.minierp.modules.order.application.exception.OrderNotFoundException;
import com.yasir.erp.minierp.modules.product.application.exception.ProductNotFoundException;
import com.yasir.erp.minierp.common.generator.UlidGenerator;
import com.yasir.erp.minierp.modules.order.application.converter.OrderConverter;
import com.yasir.erp.minierp.modules.order.application.dto.OrderDto;
import com.yasir.erp.minierp.modules.order.domain.model.Order;
import com.yasir.erp.minierp.modules.order.domain.model.OrderStatus;
import com.yasir.erp.minierp.modules.order.domain.port.inbound.command.AddItemToOrderUseCase;
import com.yasir.erp.minierp.modules.order.domain.port.outbound.command.OrderCommandPort;
import com.yasir.erp.minierp.modules.order.domain.port.outbound.query.OrderActiveQueryPort;
import com.yasir.erp.minierp.modules.orderItem.application.dto.request.CreateOrderItemDtoRequest;
import com.yasir.erp.minierp.modules.orderItem.domain.model.OrderItem;
import com.yasir.erp.minierp.modules.orderItem.domain.port.outbound.command.OrderItemCommandPort;
import com.yasir.erp.minierp.modules.product.domain.model.Product;
import com.yasir.erp.minierp.modules.product.domain.port.outbound.query.ProductQueryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class AddItemToOrderService implements AddItemToOrderUseCase {

    private final OrderActiveQueryPort orderActiveQueryPort;
    private final OrderCommandPort orderCommandPort;
    private final ProductQueryPort productQueryPort;
    private final OrderItemCommandPort orderItemCommandPort;
    private final OrderConverter orderConverter;

    public AddItemToOrderService(OrderActiveQueryPort orderActiveQueryPort,
                                 OrderCommandPort orderCommandPort,
                                 ProductQueryPort productQueryPort,
                                 OrderItemCommandPort orderItemCommandPort,
                                 OrderConverter orderConverter) {
        this.orderActiveQueryPort = orderActiveQueryPort;
        this.orderCommandPort = orderCommandPort;
        this.productQueryPort = productQueryPort;
        this.orderItemCommandPort = orderItemCommandPort;
        this.orderConverter = orderConverter;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public OrderDto addItemToOrder(String orderId,
                                   CreateOrderItemDtoRequest createOrderItemDtoRequest) {

        Order existing = orderActiveQueryPort.findByIdAndActive(orderId, true)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        if (!OrderStatus.PENDING.equals(existing.getOrderStatus())) {
            throw new OrderAlreadyFinalizedException(orderId);
        }

        Product product = productQueryPort.
                findByIdAndActive(createOrderItemDtoRequest.getProductId(), true)
                .orElseThrow(() -> new
                        ProductNotFoundException(createOrderItemDtoRequest.getProductId()));

        BigDecimal qty = BigDecimal.valueOf(createOrderItemDtoRequest.getQuantity());
        BigDecimal net = createOrderItemDtoRequest.getUnitPrice().multiply(qty);
        BigDecimal tax = net.multiply(product.getVatRate());
        BigDecimal total = net.add(tax);

        OrderItem item = new OrderItem(
                UlidGenerator.generate(), product, existing,
                createOrderItemDtoRequest.getQuantity(),
                createOrderItemDtoRequest.getUnitPrice(),
                product.getVatRate(),
                tax, total
        );
        OrderItem persisted = orderItemCommandPort.save(item);

        Set<OrderItem> updatedItems = new HashSet<>(existing.getOrderItems());
        updatedItems.add(persisted);

        BigDecimal updatedTotal = updatedItems.stream()
                .map(OrderItem::getTotalPriceWithTax)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order updated = new Order(
                existing.getId(), existing.getCustomer(), existing.getUser(),
                updatedItems, existing.getOrderStatus(), existing.getCreatedAt(),
                LocalDateTime.now(), existing.getShippingAddress(), existing.getBillingAddress(),
                existing.getOrderDate(), updatedTotal, existing.getNote(), true
        );

        return orderConverter.convertToDto(orderCommandPort.save(updated));
    }
}
