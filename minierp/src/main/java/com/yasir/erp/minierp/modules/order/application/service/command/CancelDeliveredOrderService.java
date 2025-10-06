package com.yasir.erp.minierp.modules.order.application.service.command;

import com.yasir.erp.minierp.modules.order.application.exception.OrderNotFoundException;
import com.yasir.erp.minierp.modules.dispatchNote.domain.model.DispatchNote;
import com.yasir.erp.minierp.modules.dispatchNote.domain.port.inbound.command.CancelDispatchNoteUseCase;
import com.yasir.erp.minierp.modules.dispatchNote.domain.port.outbound.query.DispatchNoteOrderQueryPort;
import com.yasir.erp.minierp.modules.invoice.domain.model.Invoice;
import com.yasir.erp.minierp.modules.invoice.domain.port.inbound.command.DeactivateInvoiceUseCase;
import com.yasir.erp.minierp.modules.invoice.domain.port.outbound.query.InvoiceOrderQueryPort;
import com.yasir.erp.minierp.modules.order.application.converter.OrderConverter;
import com.yasir.erp.minierp.modules.order.application.dto.OrderDto;
import com.yasir.erp.minierp.modules.order.domain.model.Order;
import com.yasir.erp.minierp.modules.order.domain.model.OrderStatus;
import com.yasir.erp.minierp.modules.order.domain.port.inbound.command.CancelDeliveredOrderUseCase;
import com.yasir.erp.minierp.modules.order.domain.port.outbound.command.OrderCommandPort;
import com.yasir.erp.minierp.modules.order.domain.port.outbound.query.OrderActiveQueryPort;
import com.yasir.erp.minierp.modules.orderItem.domain.model.OrderItem;
import com.yasir.erp.minierp.modules.stock.domain.port.inbound.command.IncreaseStockUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CancelDeliveredOrderService implements CancelDeliveredOrderUseCase {

    private final OrderActiveQueryPort orderActiveQueryPort;
    private final OrderCommandPort orderCommandPort;
    private final IncreaseStockUseCase increaseStockUseCase;
    private final DispatchNoteOrderQueryPort dispatchNoteOrderQueryPort;
    private final CancelDispatchNoteUseCase cancelDispatchNote;
    private final InvoiceOrderQueryPort invoiceOrderQueryPort;
    private final DeactivateInvoiceUseCase deactivateInvoice;
    private final OrderConverter orderConverter;

    public CancelDeliveredOrderService(OrderActiveQueryPort orderActiveQueryPort,
                                       OrderCommandPort orderCommandPort,
                                       IncreaseStockUseCase increaseStockUseCase,
                                       DispatchNoteOrderQueryPort dispatchNoteOrderQueryPort,
                                       CancelDispatchNoteUseCase cancelDispatchNote,
                                       InvoiceOrderQueryPort invoiceOrderQueryPort,
                                       DeactivateInvoiceUseCase deactivateInvoice,
                                       OrderConverter orderConverter) {
        this.orderActiveQueryPort = orderActiveQueryPort;
        this.orderCommandPort = orderCommandPort;
        this.increaseStockUseCase = increaseStockUseCase;
        this.dispatchNoteOrderQueryPort = dispatchNoteOrderQueryPort;
        this.cancelDispatchNote = cancelDispatchNote;
        this.invoiceOrderQueryPort = invoiceOrderQueryPort;
        this.deactivateInvoice = deactivateInvoice;
        this.orderConverter = orderConverter;
    }

    @Transactional
    public OrderDto cancelDeliveredOrder(String orderId) {
        Order order = orderActiveQueryPort.findByIdAndActive(orderId, true)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        if (OrderStatus.CANCELLED.equals(order.getOrderStatus()) ||
                !OrderStatus.DELIVERED.equals(order.getOrderStatus())) {
            throw new IllegalStateException
                    ("Sadece DELIVERED durumundaki sipariş iptal edilebilir.");
        }

        for (OrderItem it : order.getOrderItems()) {
            increaseStockUseCase.increaseStock
                    (it.getProduct().getId(), it.getQuantity());
        }

        try {
            DispatchNote dn = dispatchNoteOrderQueryPort.findByOrderIdAndActive
                    (orderId, true).orElseThrow(()->new OrderNotFoundException(orderId));

            cancelDispatchNote.cancelDispatchNote(dn.getId());
        } catch (RuntimeException ignored) {
        }

        try {
            Invoice inv = invoiceOrderQueryPort.
                    findByOrderIdAndActive(orderId, true)
                    .orElseThrow(()-> new OrderNotFoundException(orderId));
            deactivateInvoice.deactivateInvoice(inv.getId());
        } catch (RuntimeException ignored) {
        }

        Order cancelled = new Order(
                order.getId(), order.getCustomer(), order.getUser(), order.getOrderItems(),
                OrderStatus.CANCELLED, order.getCreatedAt(), LocalDateTime.now(),
                order.getShippingAddress(), order.getBillingAddress(), order.getOrderDate(),
                order.getTotalAmount(), order.getNote(), true
        );
        return orderConverter.convertToDto(orderCommandPort.save(cancelled));
    }
}
