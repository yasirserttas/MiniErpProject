package com.yasir.erp.minierp.modules.order.application.service.command;

import com.yasir.erp.minierp.common.generator.ShortIdGenerator;
import com.yasir.erp.minierp.dto.dispatchNote.request.CreateDispatchNoteDtoRequest;
import com.yasir.erp.minierp.dto.dispatchNote.request.CreateOrderDispatchedDispatchOrder;
import com.yasir.erp.minierp.modules.order.application.exception.OrderNotFoundException;
import com.yasir.erp.minierp.modules.dispatchNote.domain.port.inbound.command.
        CreateDispatchNoteUseCase;
import com.yasir.erp.minierp.modules.order.application.converter.OrderConverter;
import com.yasir.erp.minierp.modules.order.application.dto.OrderDto;
import com.yasir.erp.minierp.modules.order.domain.model.Order;
import com.yasir.erp.minierp.modules.order.domain.model.OrderStatus;
import com.yasir.erp.minierp.modules.order.domain.port.inbound.command.DispatchOrderUseCase;
import com.yasir.erp.minierp.modules.order.domain.port.outbound.command.OrderCommandPort;
import com.yasir.erp.minierp.modules.order.domain.port.outbound.query.OrderActiveQueryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class DispatchOrderService implements DispatchOrderUseCase {

    private final OrderActiveQueryPort orderActiveQueryPort;
    private final OrderCommandPort orderCommandPort;
    private final CreateDispatchNoteUseCase createDispatchNote;
    private final OrderConverter orderConverter;

    public DispatchOrderService(OrderActiveQueryPort orderActiveQueryPort,
                                OrderCommandPort orderCommandPort,
                                CreateDispatchNoteUseCase createDispatchNote,
                                OrderConverter orderConverter) {
        this.orderActiveQueryPort = orderActiveQueryPort;
        this.orderCommandPort = orderCommandPort;
        this.createDispatchNote = createDispatchNote;
        this.orderConverter = orderConverter;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public OrderDto dispatchOrder(String orderId, CreateOrderDispatchedDispatchOrder req) {

        Order order = orderActiveQueryPort.findByIdAndActive(orderId, true)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        if (!OrderStatus.PROCESSING.equals(order.getOrderStatus())) {
            throw new IllegalStateException
                    ("Sadece PROCESSING durumundaki siparişler sevk edilebilir.");
        }

        createDispatchNote.createDispatchNote(
                new CreateDispatchNoteDtoRequest(
                order.getUser().getId(),
                        order.getCustomer().getId(),
                        orderId,
                req.getTransporterName(),
                        req.getTransporterPlate(),
                        LocalDateTime.now(),
                req.getEstimatedArrival(),
                        ShortIdGenerator.generate(10),
                        req.getTransporterName(),
                        null
        ));

        Order dispatched = new Order(
                order.getId(), order.getCustomer(), order.getUser(), order.getOrderItems(),
                OrderStatus.DISPATCHED, order.getCreatedAt(), LocalDateTime.now(),
                order.getShippingAddress(), order.getBillingAddress(), order.getOrderDate(),
                order.getTotalAmount(), order.getNote(), true
        );
        return orderConverter.convertToDto(orderCommandPort.save(dispatched));
    }
}
