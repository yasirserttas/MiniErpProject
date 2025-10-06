package com.yasir.erp.minierp.modules.order.application.service.command;

import com.yasir.erp.minierp.modules.address.application.exception.AddressNotFoundException;
import com.yasir.erp.minierp.modules.customer.application.exception.CustomerNotFoundException;
import com.yasir.erp.minierp.modules.product.application.exception.ProductNotFoundException;
import com.yasir.erp.minierp.modules.user.application.exception.UserNotFoundException;
import com.yasir.erp.minierp.common.generator.UlidGenerator;
import com.yasir.erp.minierp.modules.address.domain.model.Address;
import com.yasir.erp.minierp.modules.address.domain.port.outbound.query.AddressQueryPort;
import com.yasir.erp.minierp.modules.customer.domain.model.Customer;
import com.yasir.erp.minierp.modules.customer.domain.port.outbound.
        query.CustomerActiveQueryPort;
import com.yasir.erp.minierp.modules.order.application.converter.OrderConverter;
import com.yasir.erp.minierp.modules.order.application.dto.OrderDto;
import com.yasir.erp.minierp.modules.order.application.dto.request.CreateOrderDtoRequest;
import com.yasir.erp.minierp.modules.order.domain.model.Order;
import com.yasir.erp.minierp.modules.order.domain.model.OrderStatus;
import com.yasir.erp.minierp.modules.order.domain.port.inbound.command.CreateOrderUseCase;
import com.yasir.erp.minierp.modules.order.domain.port.outbound.command.OrderCommandPort;
import com.yasir.erp.minierp.modules.orderItem.domain.model.OrderItem;
import com.yasir.erp.minierp.modules.orderItem.domain.port.outbound.
        command.OrderItemCommandPort;
import com.yasir.erp.minierp.modules.product.domain.model.Product;
import com.yasir.erp.minierp.modules.product.domain.port.outbound.query.ProductQueryPort;
import com.yasir.erp.minierp.modules.user.domain.model.User;
import com.yasir.erp.minierp.modules.user.domain.port.outbound.query.UserByIdQueryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class AddOrderService implements CreateOrderUseCase {

    private final OrderCommandPort orderCommandPort;
    private final UserByIdQueryPort userByIdQueryPort;
    private final CustomerActiveQueryPort customerActiveQueryPort;
    private final AddressQueryPort addressQueryPort;
    private final OrderItemCommandPort orderItemCommandPort;
    private final ProductQueryPort productQueryPort;
    private final OrderConverter orderConverter;

    public AddOrderService(OrderCommandPort orderCommandPort,
                           UserByIdQueryPort userByIdQueryPort,
                           CustomerActiveQueryPort customerActiveQueryPort,
                           AddressQueryPort addressQueryPort,
                           OrderItemCommandPort orderItemCommandPort,
                           ProductQueryPort productQueryPort,
                           OrderConverter orderConverter) {
        this.orderCommandPort = orderCommandPort;
        this.userByIdQueryPort = userByIdQueryPort;
        this.customerActiveQueryPort = customerActiveQueryPort;
        this.addressQueryPort = addressQueryPort;
        this.orderItemCommandPort = orderItemCommandPort;
        this.productQueryPort = productQueryPort;
        this.orderConverter = orderConverter;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public OrderDto addOrder(CreateOrderDtoRequest createOrderDtoRequest) {

        if (createOrderDtoRequest.getOrderItems() == null ||
                createOrderDtoRequest.getOrderItems().isEmpty()) {
            throw new IllegalArgumentException("Siparişte en az 1 ürün olmalıdır.");
        }


        User user = userByIdQueryPort.findById(createOrderDtoRequest.getUser().getId())
                .orElseThrow(() -> new
                        UserNotFoundException(createOrderDtoRequest.getUser().getId()));

        Customer customer = customerActiveQueryPort
                .findByIdAndActive(createOrderDtoRequest.getCustomer().getId(), true)
                .orElseThrow(() -> new
                        CustomerNotFoundException(createOrderDtoRequest.getCustomer().getId()));

        Address shipping = addressQueryPort.findById(createOrderDtoRequest.getShippingAddressId())
                .orElseThrow(() ->
                        new AddressNotFoundException
                                (createOrderDtoRequest.getShippingAddressId()));
        Address billing = addressQueryPort.findById(createOrderDtoRequest.getBillingAddressId())
                .orElseThrow(() -> new
                        AddressNotFoundException(createOrderDtoRequest.getBillingAddressId()));


        LocalDateTime now = LocalDateTime.now();
        Set<OrderItem> emptyItems = new HashSet<>();

        Order draft = new Order(
                UlidGenerator.generate(),
                customer,
                user,
                emptyItems,
                OrderStatus.PENDING,
                now,
                now,
                shipping,
                billing,
                now,
                BigDecimal.ZERO,
                createOrderDtoRequest.getNote(),
                true
        );

        Order savedOrder = orderCommandPort.save(draft);


        Set<OrderItem> items = new HashSet<>();
        for (var itemDto : createOrderDtoRequest.getOrderItems()) {
            Product product = productQueryPort
                    .findByIdAndActive(itemDto.getProductId(), true)
                    .orElseThrow(()-> new ProductNotFoundException(itemDto.getProductId()));

            BigDecimal qty = BigDecimal.valueOf(itemDto.getQuantity());
            BigDecimal lineNet = itemDto.getUnitPrice().multiply(qty);
            BigDecimal taxRate = product.getVatRate();
            BigDecimal taxAmount = lineNet.multiply(taxRate);
            BigDecimal lineTotal = lineNet.add(taxAmount);

            OrderItem newItem = new OrderItem(
                    UlidGenerator.generate(),
                    product,
                    savedOrder,
                    itemDto.getQuantity(),
                    itemDto.getUnitPrice(),
                    taxRate,
                    taxAmount,
                    lineTotal
            );

            items.add(orderItemCommandPort.save(newItem));
        }


        BigDecimal total = items.stream()
                .map(OrderItem::getTotalPriceWithTax)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order finalized = new Order(
                savedOrder.getId(),
                savedOrder.getCustomer(),
                savedOrder.getUser(),
                items,
                savedOrder.getOrderStatus(),
                savedOrder.getCreatedAt(),
                LocalDateTime.now(),
                savedOrder.getShippingAddress(),
                savedOrder.getBillingAddress(),
                savedOrder.getOrderDate(),
                total,
                savedOrder.getNote(),
                true
        );

        Order persisted = orderCommandPort.save(finalized);
        return orderConverter.convertToDto(persisted);
    }
}
