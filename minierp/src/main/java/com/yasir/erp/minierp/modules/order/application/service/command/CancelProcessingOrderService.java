package com.yasir.erp.minierp.modules.order.application.service.command;

import com.yasir.erp.minierp.modules.invoice.application.dto.InvoiceDto;
import com.yasir.erp.minierp.modules.order.application.exception.OrderNotFoundException;
import com.yasir.erp.minierp.modules.invoice.domain.port.inbound.command.DeactivateInvoiceUseCase;
import com.yasir.erp.minierp.modules.invoice.domain.port.inbound.query.FindInvoiceByOrderUseCase;
import com.yasir.erp.minierp.modules.order.application.converter.OrderConverter;
import com.yasir.erp.minierp.modules.order.application.dto.OrderDto;
import com.yasir.erp.minierp.modules.order.domain.model.Order;
import com.yasir.erp.minierp.modules.order.domain.model.OrderStatus;
import com.yasir.erp.minierp.modules.order.domain.port.inbound.command.CancelProcessingOrderUseCase;
import com.yasir.erp.minierp.modules.order.domain.port.outbound.command.OrderCommandPort;
import com.yasir.erp.minierp.modules.order.domain.port.outbound.query.OrderActiveQueryPort;
import com.yasir.erp.minierp.modules.orderItem.domain.model.OrderItem;
import com.yasir.erp.minierp.modules.stock.domain.port.inbound.command.IncreaseStockUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CancelProcessingOrderService implements CancelProcessingOrderUseCase {

    private final OrderActiveQueryPort orderActiveQueryPort;
    private final OrderCommandPort orderCommandPort;
    private final IncreaseStockUseCase increaseStockUseCase;
    private final FindInvoiceByOrderUseCase findInvoiceByOrder;
    private final DeactivateInvoiceUseCase deactivateInvoice;
    private final OrderConverter orderConverter;

    public CancelProcessingOrderService(OrderActiveQueryPort orderActiveQueryPort,
                                        OrderCommandPort orderCommandPort,
                                        IncreaseStockUseCase increaseStockUseCase,
                                        FindInvoiceByOrderUseCase findInvoiceByOrder,
                                        DeactivateInvoiceUseCase deactivateInvoice,
                                        OrderConverter orderConverter) {
        this.orderActiveQueryPort = orderActiveQueryPort;
        this.orderCommandPort = orderCommandPort;
        this.increaseStockUseCase = increaseStockUseCase;
        this.findInvoiceByOrder = findInvoiceByOrder;
        this.deactivateInvoice = deactivateInvoice;
        this.orderConverter = orderConverter;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public OrderDto cancelProcessingOrder(String orderId) {

        Order order = orderActiveQueryPort.findByIdAndActive(orderId, true)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        if (OrderStatus.CANCELLED.equals(order.getOrderStatus()) ||
                !OrderStatus.PROCESSING.equals(order.getOrderStatus())) {
            throw new IllegalStateException
                    ("Sadece PROCESSING durumundaki sipariş iptal edilebilir.");
        }

        for (OrderItem it : order.getOrderItems()) {
            increaseStockUseCase.increaseStock(it.getProduct().getId(), it.getQuantity());
        }

        InvoiceDto inv = findInvoiceByOrder.findDtoByOrder_IdAndActive(orderId, true);
        deactivateInvoice.deactivateInvoice(inv.getId());

        Order cancelled = new Order(
                order.getId(), order.getCustomer(), order.getUser(), order.getOrderItems(),
                OrderStatus.CANCELLED, order.getCreatedAt(), LocalDateTime.now(),
                order.getShippingAddress(), order.getBillingAddress(), order.getOrderDate(),
                order.getTotalAmount(), order.getNote(), true
        );
        return orderConverter.convertToDto(orderCommandPort.save(cancelled));
    }
}
