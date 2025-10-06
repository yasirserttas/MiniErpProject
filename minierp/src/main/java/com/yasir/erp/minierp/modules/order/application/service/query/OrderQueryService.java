package com.yasir.erp.minierp.modules.order.application.service.query;

import com.yasir.erp.minierp.modules.customer.application.exception.CustomerNotFoundException;
import com.yasir.erp.minierp.modules.order.application.exception.OrderNotFoundException;
import com.yasir.erp.minierp.modules.order.application.converter.OrderConverter;
import com.yasir.erp.minierp.modules.order.application.dto.OrderDto;
import com.yasir.erp.minierp.modules.order.domain.model.Order;
import com.yasir.erp.minierp.modules.order.domain.model.OrderStatus;
import com.yasir.erp.minierp.modules.order.domain.port.inbound.query.*;
import com.yasir.erp.minierp.modules.order.domain.port.outbound.query.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class OrderQueryService implements FindOrderByCustomerUseCase,
        FindOrderByIdUseCase,
        FindOrderByStatusUseCase,
        FindOrderByUserUseCase,
        ListOrdersByCreatedAtUseCase,
        ListOrdersByCustomerUseCase,
        ListOrdersByOrderDateUseCase,
        ListOrdersByStatusAndCreatedAtUseCase,
        ListOrdersByStatusAndOrderDateUseCase,
        ListOrdersByStatusUseCase,
        ListOrdersByUpdatedAtUseCase,
        ListOrdersByUserUseCase {


    private final OrderConverter orderConverter;
    private final OrderActiveQueryPort orderActiveQueryPort;
    private final OrderCreatedAtQueryPort orderCreatedAtQueryPort;
    private final OrderCustomerQueryPort orderCustomerQueryPort;
    private final OrderDateQueryPort orderDateQueryPort;
    private final OrderStatusCreatedAtQueryPort orderStatusCreatedAtQueryPort;
    private final OrderStatusOrderDateQueryPort orderStatusOrderDateQueryPort;
    private final OrderStatusQueryPort orderStatusQueryPort;
    private final OrderUpdatedAtQueryPort orderUpdatedAtQueryPort;
    private final OrderUserQueryPort orderUserQueryPort;


    public OrderQueryService(OrderConverter orderConverter,
                             OrderActiveQueryPort orderActiveQueryPort,
                             OrderCreatedAtQueryPort orderCreatedAtQueryPort,
                             OrderCustomerQueryPort orderCustomerQueryPort,
                             OrderDateQueryPort orderDateQueryPort,
                             OrderStatusCreatedAtQueryPort orderStatusCreatedAtQueryPort,
                             OrderStatusOrderDateQueryPort orderStatusOrderDateQueryPort,
                             OrderStatusQueryPort orderStatusQueryPort,
                             OrderUpdatedAtQueryPort orderUpdatedAtQueryPort,
                             OrderUserQueryPort orderUserQueryPort) {
        this.orderConverter = orderConverter;
        this.orderActiveQueryPort = orderActiveQueryPort;
        this.orderCreatedAtQueryPort = orderCreatedAtQueryPort;
        this.orderCustomerQueryPort = orderCustomerQueryPort;
        this.orderDateQueryPort = orderDateQueryPort;
        this.orderStatusCreatedAtQueryPort = orderStatusCreatedAtQueryPort;
        this.orderStatusOrderDateQueryPort = orderStatusOrderDateQueryPort;
        this.orderStatusQueryPort = orderStatusQueryPort;
        this.orderUpdatedAtQueryPort = orderUpdatedAtQueryPort;
        this.orderUserQueryPort = orderUserQueryPort;
    }



    @Override
    public OrderDto findDtoByCustomerIdAndActive(UUID customerId, boolean active) {
        return orderCustomerQueryPort.findByCustomerIdAndActive(customerId, active)
                .map(orderConverter::convertToDto)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
    }

    @Override
    public OrderDto findDtoByIdAndActive(String id, boolean active) {
        return orderActiveQueryPort.findByIdAndActive(id, active)
                .map(orderConverter::convertToDto)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    @Override
    public OrderDto findDtoByOrderStatusAndActive(OrderStatus status, boolean active) {
        return orderStatusQueryPort.findByOrderStatusAndActive(status, active)
                .map(orderConverter::convertToDto)
                .orElseThrow(() -> new OrderNotFoundException
                        ("Durumu " + status + " olan aktif bir sipariş bulunamadı."));
    }

    @Override
    public OrderDto findDtoByUserIdAndActive(UUID userId, boolean active) {
        return orderUserQueryPort.findByUserIdAndActive(userId, active)
                .map(orderConverter::convertToDto)
                .orElseThrow(() -> new OrderNotFoundException
                        ("Kullanıcı ID'si " + userId + " olan aktif bir sipariş bulunamadı."));
    }


    @Override
    public Set<OrderDto> findAllDtoByCreatedAtBetweenAndActive
            (LocalDateTime start, LocalDateTime end, boolean active) {
        Set<Order> orders = findAllByCreatedAtBetweenAndActive(start, end, active);
        return orderConverter.convertToSetDto(orders);
    }

    @Override
    public Set<OrderDto> findAllDtoByCustomerIdAndActive(UUID customerId, boolean active) {
        Set<Order> orders = findAllByCustomerIdAndActive(customerId, active);
        return orderConverter.convertToSetDto(orders);
    }

    @Override
    public Set<OrderDto> findAllDtoByOrderDateBetweenAndActive
            (LocalDateTime start, LocalDateTime end, boolean active) {
        Set<Order> orders = findAllByOrderDateBetweenAndActive(start, end, active);
        return orderConverter.convertToSetDto(orders);
    }

    @Override
    public Set<OrderDto> findAllDtoByOrderStatusAndCreatedAtBetweenAndActive
            (OrderStatus status, LocalDateTime start, LocalDateTime end, boolean active) {
        Set<Order> orders = findAllByOrderStatusAndCreatedAtBetweenAndActive
                (status, start, end, active);
        return orderConverter.convertToSetDto(orders);
    }

    @Override
    public Set<OrderDto> findAllDtoByOrderStatusAndOrderDateBetweenAndActive
            (OrderStatus status, LocalDateTime start, LocalDateTime end, boolean active) {
        Set<Order> orders = findAllByOrderStatusAndOrderDateBetweenAndActive
                (status, start, end, active);
        return orderConverter.convertToSetDto(orders);
    }

    @Override
    public Set<OrderDto> findAllDtoByOrderStatusAndActive(OrderStatus status, boolean active) {
        Set<Order> orders = findAllByOrderStatusAndActive(status, active);
        return orderConverter.convertToSetDto(orders);
    }

    @Override
    public Set<OrderDto> findAllDtoByUpdatedAtBetweenAndActive(LocalDateTime start, LocalDateTime end, boolean active) {
        Set<Order> orders = findAllByUpdatedAtBetweenAndActive(start, end, active);
        return orderConverter.convertToSetDto(orders);
    }

    @Override
    public Set<OrderDto> findAllDtoByUserIdAndActive(UUID userId, boolean active) {
        Set<Order> orders = findAllByUserIdAndActive(userId, active);
        return orderConverter.convertToSetDto(orders);
    }


    protected Set<Order> findAllByUserIdAndActive(UUID userId, boolean active) {
        return orderUserQueryPort.findAllByUserIdAndActive(userId, active);
    }

    protected Set<Order> findAllByUpdatedAtBetweenAndActive
            (LocalDateTime start, LocalDateTime end, boolean active) {
        return orderUpdatedAtQueryPort.findAllByUpdatedAtBetweenAndActive(start, end, active);
    }

    protected Set<Order> findAllByOrderStatusAndActive(OrderStatus status, boolean active) {
        return orderStatusQueryPort.findAllByOrderStatusAndActive(status, active);
    }

    protected Set<Order> findAllByOrderStatusAndOrderDateBetweenAndActive
            (OrderStatus status, LocalDateTime start, LocalDateTime end, boolean active) {
        return orderStatusOrderDateQueryPort.
                findAllByOrderStatusAndOrderDateBetweenAndActive(status, start, end, active);
    }

    protected Set<Order> findAllByOrderStatusAndCreatedAtBetweenAndActive
            (OrderStatus status, LocalDateTime start, LocalDateTime end, boolean active) {
        return orderStatusCreatedAtQueryPort.
                findAllByOrderStatusAndCreatedAtBetweenAndActive(status, start, end, active);
    }

    protected Set<Order> findAllByOrderDateBetweenAndActive
            (LocalDateTime start, LocalDateTime end, boolean active){
        return orderDateQueryPort.findAllByOrderDateBetweenAndActive(start, end, active);
    }

    protected Set<Order> findAllByCustomerIdAndActive(UUID customerId, boolean active){
        return orderCustomerQueryPort.findAllByCustomerIdAndActive(customerId, active);
    }

    protected Set<Order> findAllByCreatedAtBetweenAndActive
            (LocalDateTime start, LocalDateTime end, boolean active){
        return orderCreatedAtQueryPort.findAllByCreatedAtBetweenAndActive(start, end, active);
    }
}