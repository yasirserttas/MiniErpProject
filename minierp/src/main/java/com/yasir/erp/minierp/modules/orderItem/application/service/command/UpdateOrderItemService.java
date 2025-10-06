package com.yasir.erp.minierp.modules.orderItem.application.service.command;

import com.yasir.erp.minierp.modules.orderItem.application.exception.OrderItemNotFoundException;
import com.yasir.erp.minierp.modules.order.domain.port.inbound.command.RecalculateTotalAmountUseCase;
import com.yasir.erp.minierp.modules.orderItem.application.converter.OrderItemConverter;
import com.yasir.erp.minierp.modules.orderItem.application.dto.OrderItemDto;
import com.yasir.erp.minierp.modules.orderItem.application.dto.request.UpdateOrderItemDtoRequest;
import com.yasir.erp.minierp.modules.orderItem.domain.model.OrderItem;
import com.yasir.erp.minierp.modules.orderItem.domain.port.inbound.command.UpdateOrderItemUseCase;
import com.yasir.erp.minierp.modules.orderItem.domain.port.outbound.command.OrderItemCommandPort;
import com.yasir.erp.minierp.modules.orderItem.domain.port.outbound.query.OrderItemQueryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class UpdateOrderItemService implements UpdateOrderItemUseCase {

    private final OrderItemQueryPort orderItemQueryPort;
    private final OrderItemCommandPort orderItemCommandPort;
    private final OrderItemConverter orderItemConverter;
    private final RecalculateTotalAmountUseCase recalculateTotalAmount;

    public UpdateOrderItemService(OrderItemQueryPort orderItemQueryPort,
                                  OrderItemCommandPort orderItemCommandPort,
                                  OrderItemConverter orderItemConverter,
                                  RecalculateTotalAmountUseCase recalculateTotalAmount) {
        this.orderItemQueryPort = orderItemQueryPort;
        this.orderItemCommandPort = orderItemCommandPort;
        this.orderItemConverter = orderItemConverter;
        this.recalculateTotalAmount = recalculateTotalAmount;
    }

    @Override
    @Transactional
    public OrderItemDto updateOrderItem(UpdateOrderItemDtoRequest updateOrderItemDtoRequest) {
        OrderItem existing = orderItemQueryPort.findById(updateOrderItemDtoRequest.getId())
                .orElseThrow(() -> new OrderItemNotFoundException
                        ("OrderItem not found: " + updateOrderItemDtoRequest.getId()));

        BigDecimal quantity = BigDecimal.valueOf(updateOrderItemDtoRequest.getQuantity());
        BigDecimal unitPrice = updateOrderItemDtoRequest.getUnitPrice();
        BigDecimal taxRate = existing.getProduct().getVatRate();
        BigDecimal taxAmount = unitPrice.multiply(quantity).multiply(taxRate)
                .divide(BigDecimal.valueOf(100));
        BigDecimal totalWithTax = unitPrice.multiply(quantity).add(taxAmount);

        OrderItem updated = new OrderItem(
                existing.getId(),
                existing.getProduct(),
                existing.getOrder(),
                updateOrderItemDtoRequest.getQuantity(),
                unitPrice,
                taxRate,
                taxAmount,
                totalWithTax
        );

        OrderItem saved = orderItemCommandPort.save(updated);
        recalculateTotalAmount.recalculateTotalAmount(existing.getOrder().getId());
        return orderItemConverter.convertToOrderItemDto(saved);
    }
}
