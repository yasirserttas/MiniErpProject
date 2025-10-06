package com.yasir.erp.minierp.modules.orderItem.application.service.command;

import com.yasir.erp.minierp.modules.orderItem.application.exception.OrderItemNotFoundException;
import com.yasir.erp.minierp.common.generator.UlidGenerator;
import com.yasir.erp.minierp.modules.order.domain.model.Order;
import com.yasir.erp.minierp.modules.order.domain.port.inbound.command.RecalculateTotalAmountUseCase;
import com.yasir.erp.minierp.modules.order.domain.port.outbound.query.OrderActiveQueryPort;
import com.yasir.erp.minierp.modules.orderItem.application.converter.OrderItemConverter;
import com.yasir.erp.minierp.modules.orderItem.application.dto.OrderItemDto;
import com.yasir.erp.minierp.modules.orderItem.application.dto.request.CreateOrderItemDtoRequest;
import com.yasir.erp.minierp.modules.orderItem.domain.model.OrderItem;
import com.yasir.erp.minierp.modules.orderItem.domain.port.inbound.command.CreateOrderItemUseCase;
import com.yasir.erp.minierp.modules.orderItem.domain.port.outbound.command.OrderItemCommandPort;
import com.yasir.erp.minierp.modules.product.domain.model.Product;
import com.yasir.erp.minierp.modules.product.domain.port.outbound.query.ProductQueryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class CreateOrderItemService implements CreateOrderItemUseCase {

    private final OrderItemCommandPort orderItemCommandPort;
    private final OrderItemConverter orderItemConverter;
    private final ProductQueryPort productQueryPort;
    private final OrderActiveQueryPort orderActiveQueryPort;
    private final RecalculateTotalAmountUseCase recalculateTotalAmount;

    public CreateOrderItemService(OrderItemCommandPort orderItemCommandPort,
                                  OrderItemConverter orderItemConverter,
                                  ProductQueryPort productQueryPort,
                                  OrderActiveQueryPort orderActiveQueryPort,
                                  RecalculateTotalAmountUseCase recalculateTotalAmount) {
        this.orderItemCommandPort = orderItemCommandPort;
        this.orderItemConverter = orderItemConverter;
        this.productQueryPort = productQueryPort;
        this.orderActiveQueryPort = orderActiveQueryPort;
        this.recalculateTotalAmount = recalculateTotalAmount;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public OrderItemDto createOrderItem(CreateOrderItemDtoRequest createOrderItemDtoRequest) {
        Product product = productQueryPort
                .findByIdAndActive(createOrderItemDtoRequest.getProductId(), true)
                .orElseThrow(() -> new OrderItemNotFoundException
                        ("Product not found: " + createOrderItemDtoRequest.getProductId()));
        Order order = orderActiveQueryPort
                .findByIdAndActive(createOrderItemDtoRequest.getOrderId(), true)
                .orElseThrow(() -> new OrderItemNotFoundException
                        ("Order not found: " + createOrderItemDtoRequest.getOrderId()));

        BigDecimal quantity = BigDecimal.valueOf(createOrderItemDtoRequest.getQuantity());
        BigDecimal unitPrice = createOrderItemDtoRequest.getUnitPrice();
        BigDecimal taxRate = product.getVatRate();
        BigDecimal taxAmount = unitPrice.multiply(quantity).multiply(taxRate)
                .divide(BigDecimal.valueOf(100));
        BigDecimal totalWithTax = unitPrice.multiply(quantity).add(taxAmount);

        OrderItem item = new OrderItem(
                UlidGenerator.generate(),
                product,
                order,
                createOrderItemDtoRequest.getQuantity(),
                unitPrice,
                taxRate,
                taxAmount,
                totalWithTax
        );

        OrderItem saved = orderItemCommandPort.save(item);
        recalculateTotalAmount.recalculateTotalAmount(order.getId());
        return orderItemConverter.convertToOrderItemDto(saved);
    }
}
