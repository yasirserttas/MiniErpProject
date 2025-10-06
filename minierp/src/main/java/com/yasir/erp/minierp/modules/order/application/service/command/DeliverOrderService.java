package com.yasir.erp.minierp.modules.order.application.service.command;

import com.yasir.erp.minierp.dto.dispatchNote.request.UpdateDispatchNoteDtoRequest;
import com.yasir.erp.minierp.dto.dispatchNote.request.UpdateOrderDeliveredDispatchNoteDtoRequest;
import com.yasir.erp.minierp.modules.order.application.exception.OrderNotFoundException;
import com.yasir.erp.minierp.modules.dispatchNote.domain.model.DispatchNote;
import com.yasir.erp.minierp.modules.dispatchNote.domain.port.inbound.
        command.UpdateDispatchNoteUseCase;
import com.yasir.erp.minierp.modules.dispatchNote.domain.port.outbound.
        query.DispatchNoteOrderQueryPort;
import com.yasir.erp.minierp.modules.order.application.converter.OrderConverter;
import com.yasir.erp.minierp.modules.order.application.dto.OrderDto;
import com.yasir.erp.minierp.modules.order.domain.model.Order;
import com.yasir.erp.minierp.modules.order.domain.model.OrderStatus;
import com.yasir.erp.minierp.modules.order.domain.port.inbound.command.DeliverOrderUseCase;
import com.yasir.erp.minierp.modules.order.domain.port.outbound.command.OrderCommandPort;
import com.yasir.erp.minierp.modules.order.domain.port.outbound.query.OrderActiveQueryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class DeliverOrderService implements DeliverOrderUseCase {

    private final OrderActiveQueryPort orderActiveQueryPort;
    private final OrderCommandPort orderCommandPort;
    private final DispatchNoteOrderQueryPort dispatchNoteOrderQueryPort;
    private final UpdateDispatchNoteUseCase updateDispatchNote;
    private final OrderConverter orderConverter;

    public DeliverOrderService(OrderActiveQueryPort orderActiveQueryPort,
                               OrderCommandPort orderCommandPort,
                               DispatchNoteOrderQueryPort dispatchNoteOrderQueryPort,
                               UpdateDispatchNoteUseCase updateDispatchNote,
                               OrderConverter orderConverter) {
        this.orderActiveQueryPort = orderActiveQueryPort;
        this.orderCommandPort = orderCommandPort;
        this.dispatchNoteOrderQueryPort = dispatchNoteOrderQueryPort;
        this.updateDispatchNote = updateDispatchNote;
        this.orderConverter = orderConverter;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public OrderDto deliverOrder(String orderId, UpdateOrderDeliveredDispatchNoteDtoRequest req) {

        Order order = orderActiveQueryPort.findByIdAndActive(orderId, true)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        if (!OrderStatus.DISPATCHED.equals(order.getOrderStatus())) {
            throw new IllegalStateException
                    ("Sadece DISPATCHED durumundaki siparişler teslim edilebilir.");
        }

        DispatchNote dispatchNote = dispatchNoteOrderQueryPort.findByOrderIdAndActive
                (orderId, true).orElseThrow(()-> new OrderNotFoundException(orderId));

        updateDispatchNote.updateDispatchNote(new UpdateDispatchNoteDtoRequest(
                dispatchNote.getId(), dispatchNote.getTransporterName(),
                dispatchNote.getTransporterPlate(),
                LocalDateTime.now(), dispatchNote.getDispatchNoteNumber(),
                dispatchNote.getDeliveredBy(), req.getReceivedBy()
        ));

        Order delivered = new Order(
                order.getId(), order.getCustomer(), order.getUser(), order.getOrderItems(),
                OrderStatus.DELIVERED, order.getCreatedAt(), LocalDateTime.now(),
                order.getShippingAddress(), order.getBillingAddress(), order.getOrderDate(),
                order.getTotalAmount(), order.getNote(), true
        );
        return orderConverter.convertToDto(orderCommandPort.save(delivered));
    }
}
