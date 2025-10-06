package com.yasir.erp.minierp.modules.order.application.service.command;

import com.yasir.erp.minierp.modules.order.application.exception.OrderNotFoundException;
import com.yasir.erp.minierp.common.generator.ShortIdGenerator;
import com.yasir.erp.minierp.modules.invoice.application.dto.request.CreateInvoiceDtoRequest;
import com.yasir.erp.minierp.modules.invoice.domain.port.inbound.command.CreateInvoiceUseCase;
import com.yasir.erp.minierp.modules.order.application.converter.OrderConverter;
import com.yasir.erp.minierp.modules.order.application.dto.OrderDto;
import com.yasir.erp.minierp.modules.order.domain.model.Order;
import com.yasir.erp.minierp.modules.order.domain.model.OrderStatus;
import com.yasir.erp.minierp.modules.order.domain.port.inbound.command.ProcessingOrderUseCase;
import com.yasir.erp.minierp.modules.order.domain.port.outbound.command.OrderCommandPort;
import com.yasir.erp.minierp.modules.order.domain.port.outbound.query.OrderActiveQueryPort;
import com.yasir.erp.minierp.modules.orderItem.domain.model.OrderItem;
import com.yasir.erp.minierp.modules.stock.domain.port.inbound.command.DecreaseStockUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ProcessingOrderService implements ProcessingOrderUseCase {

    private final OrderActiveQueryPort orderActiveQueryPort;
    private final OrderCommandPort orderCommandPort;
    private final DecreaseStockUseCase decreaseStockUseCase;
    private final CreateInvoiceUseCase createInvoice;
    private final OrderConverter orderConverter;

    public ProcessingOrderService(OrderActiveQueryPort orderActiveQueryPort,
                                  OrderCommandPort orderCommandPort,
                                  DecreaseStockUseCase decreaseStockUseCase,
                                  CreateInvoiceUseCase createInvoice,
                                  OrderConverter orderConverter) {
        this.orderActiveQueryPort = orderActiveQueryPort;
        this.orderCommandPort = orderCommandPort;
        this.decreaseStockUseCase = decreaseStockUseCase;
        this.createInvoice = createInvoice;
        this.orderConverter = orderConverter;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public OrderDto processingOrder(String orderId) {
        Order order = orderActiveQueryPort.findByIdAndActive(orderId, true)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        if (!OrderStatus.PENDING.equals(order.getOrderStatus())) {
            throw new IllegalStateException("Sadece PENDING siparişler onaylanabilir.");
        }

        for (OrderItem it : order.getOrderItems()) {
            decreaseStockUseCase.decreaseStock(it.getProduct().getId(), it.getQuantity());
        }

        createInvoice.addInvoice(new CreateInvoiceDtoRequest(
                ShortIdGenerator.generate(10), order.getUser().getId(),
                order.getCustomer().getId(), order.getId(), LocalDateTime.now()
        ));

        Order processing = new Order(
                order.getId(), order.getCustomer(), order.getUser(), order.getOrderItems(),
                OrderStatus.PROCESSING, order.getCreatedAt(), LocalDateTime.now(),
                order.getShippingAddress(), order.getBillingAddress(), order.getOrderDate(),
                order.getTotalAmount(), order.getNote(), true
        );
        return orderConverter.convertToDto(orderCommandPort.save(processing));
    }
}
