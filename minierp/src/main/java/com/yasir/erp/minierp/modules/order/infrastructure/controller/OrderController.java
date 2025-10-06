package com.yasir.erp.minierp.modules.order.infrastructure.controller;

import com.yasir.erp.minierp.modules.order.application.dto.OrderDto;
import com.yasir.erp.minierp.modules.order.application.dto.request.CreateOrderDtoRequest;
import com.yasir.erp.minierp.modules.order.application.dto.request.UpdateOrderDtoRequest;
import com.yasir.erp.minierp.modules.order.application.service.query.OrderQueryService;
import com.yasir.erp.minierp.modules.order.domain.model.OrderStatus;
import com.yasir.erp.minierp.modules.order.domain.port.inbound.command.*;
import com.yasir.erp.minierp.modules.orderItem.application.dto.request.CreateOrderItemDtoRequest;
import com.yasir.erp.minierp.dto.dispatchNote.request.CreateOrderDispatchedDispatchOrder;
import com.yasir.erp.minierp.dto.dispatchNote.request.UpdateOrderDeliveredDispatchNoteDtoRequest;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    
    private final CreateOrderUseCase createOrder;
    private final UpdateOrderUseCase updateOrder;
    private final ActivateOrderUseCase activateOrder;
    private final DeactivateOrderUseCase deactivateOrder;
    private final AddItemToOrderUseCase addItemToOrder;
    private final ProcessingOrderUseCase processingOrder;
    private final DispatchOrderUseCase dispatchOrder;
    private final DeliverOrderUseCase deliverOrder;
    private final CancelPendingOrderUseCase cancelPendingOrder;
    private final CancelProcessingOrderUseCase cancelProcessingOrder;
    private final CancelDispatchOrderUseCase cancelDispatchOrder;
    private final CancelDeliveredOrderUseCase cancelDeliveredOrder;
    private final RecalculateTotalAmountUseCase recalcTotal;

    
    private final OrderQueryService queries;

    public OrderController(CreateOrderUseCase createOrder,
                           UpdateOrderUseCase updateOrder,
                           ActivateOrderUseCase activateOrder,
                           DeactivateOrderUseCase deactivateOrder,
                           AddItemToOrderUseCase addItemToOrder,
                           ProcessingOrderUseCase processingOrder,
                           DispatchOrderUseCase dispatchOrder,
                           DeliverOrderUseCase deliverOrder,
                           CancelPendingOrderUseCase cancelPendingOrder,
                           CancelProcessingOrderUseCase cancelProcessingOrder,
                           CancelDispatchOrderUseCase cancelDispatchOrder,
                           CancelDeliveredOrderUseCase cancelDeliveredOrder,
                           RecalculateTotalAmountUseCase recalcTotal,
                           OrderQueryService queries) {
        this.createOrder = createOrder;
        this.updateOrder = updateOrder;
        this.activateOrder = activateOrder;
        this.deactivateOrder = deactivateOrder;
        this.addItemToOrder = addItemToOrder;
        this.processingOrder = processingOrder;
        this.dispatchOrder = dispatchOrder;
        this.deliverOrder = deliverOrder;
        this.cancelPendingOrder = cancelPendingOrder;
        this.cancelProcessingOrder = cancelProcessingOrder;
        this.cancelDispatchOrder = cancelDispatchOrder;
        this.cancelDeliveredOrder = cancelDeliveredOrder;
        this.recalcTotal = recalcTotal;
        this.queries = queries;
    }

    
    @PostMapping
    public ResponseEntity<OrderDto> create(@Valid @RequestBody
                                               CreateOrderDtoRequest createOrderDtoRequest) {
        OrderDto created = createOrder.addOrder(createOrderDtoRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getById(@PathVariable String id,
                                            @RequestParam(defaultValue = "true") boolean active) {
        return ResponseEntity.ok(queries.findDtoByIdAndActive(id, active));
    }

    @GetMapping("/by-customer/{customerId}")
    public ResponseEntity<OrderDto> getByCustomer(@PathVariable UUID customerId,
                                                  @RequestParam(defaultValue = "true") boolean active) {
        return ResponseEntity.ok(queries.findDtoByCustomerIdAndActive(customerId, active));
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<OrderDto> getByUser(@PathVariable UUID userId,
                                              @RequestParam(defaultValue = "true") boolean active) {
        return ResponseEntity.ok(queries.findDtoByUserIdAndActive(userId, active));
    }

    @GetMapping("/by-status")
    public ResponseEntity<OrderDto> getByStatus(@RequestParam OrderStatus status,
                                                @RequestParam(defaultValue = "true") boolean active) {
        return ResponseEntity.ok(queries.findDtoByOrderStatusAndActive(status, active));
    }

    
    @GetMapping("/created-between")
    public ResponseEntity<Set<OrderDto>> listByCreatedBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @RequestParam(defaultValue = "true") boolean active) {
        return ResponseEntity.ok(queries.findAllDtoByCreatedAtBetweenAndActive(start, end, active));
    }

    @GetMapping("/customer/{customerId}/all")
    public ResponseEntity<Set<OrderDto>> listByCustomerAll(@PathVariable UUID customerId,
                                                           @RequestParam(defaultValue = "true")
                                                           boolean active) {
        return ResponseEntity.ok(queries.findAllDtoByCustomerIdAndActive(customerId, active));
    }

    @GetMapping("/order-date-between")
    public ResponseEntity<Set<OrderDto>> listByOrderDateBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @RequestParam(defaultValue = "true") boolean active) {
        return ResponseEntity.ok(queries.findAllDtoByOrderDateBetweenAndActive(start, end, active));
    }

    @GetMapping("/status/{status}/created-between")
    public ResponseEntity<Set<OrderDto>> listByStatusAndCreatedBetween(
            @PathVariable OrderStatus status,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @RequestParam(defaultValue = "true") boolean active) {
        return ResponseEntity.ok(
                queries.findAllDtoByOrderStatusAndCreatedAtBetweenAndActive(status, start, end, active)
        );
    }

    @GetMapping("/status/{status}/order-date-between")
    public ResponseEntity<Set<OrderDto>> listByStatusAndOrderDateBetween(
            @PathVariable OrderStatus status,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @RequestParam(defaultValue = "true") boolean active) {
        return ResponseEntity.ok(
                queries.findAllDtoByOrderStatusAndOrderDateBetweenAndActive(status, start, end, active)
        );
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Set<OrderDto>> listByStatus(@PathVariable OrderStatus status,
                                                      @RequestParam(defaultValue = "true") boolean active) {
        return ResponseEntity.ok(queries.findAllDtoByOrderStatusAndActive(status, active));
    }

    @GetMapping("/updated-between")
    public ResponseEntity<Set<OrderDto>> listByUpdatedBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @RequestParam(defaultValue = "true") boolean active) {
        return ResponseEntity.ok(queries.findAllDtoByUpdatedAtBetweenAndActive(start, end, active));
    }

    @GetMapping("/user/{userId}/all")
    public ResponseEntity<Set<OrderDto>> listByUserAll(@PathVariable UUID userId,
                                                       @RequestParam(defaultValue = "true")
                                                       boolean active) {
        return ResponseEntity.ok(queries.findAllDtoByUserIdAndActive(userId, active));
    }

    @PutMapping
    public ResponseEntity<OrderDto> update(@Valid @RequestBody UpdateOrderDtoRequest
                                                   updateOrderDtoRequest) {
        return ResponseEntity.ok(updateOrder.updateOrder(updateOrderDtoRequest));
    }
    
    @PostMapping("/{id}/items")
    public ResponseEntity<OrderDto> addItem(@PathVariable String id,
                                            @Valid @RequestBody CreateOrderItemDtoRequest
                                                    createOrderItemDtoRequest) {
        return ResponseEntity.ok(addItemToOrder.addItemToOrder(id, createOrderItemDtoRequest));
    }

    @PostMapping("/{id}/activate")
    public ResponseEntity<OrderDto> activate(@PathVariable String id) {
        return ResponseEntity.ok(activateOrder.activeOrder(id));
    }

    @PostMapping("/{id}/deactivate")
    public ResponseEntity<OrderDto> deactivate(@PathVariable String id) {
        return ResponseEntity.ok(deactivateOrder.deactivateOrder(id));
    }

    @PostMapping("/{id}/processing")
    public ResponseEntity<OrderDto> processing(@PathVariable String id) {
        return ResponseEntity.ok(processingOrder.processingOrder(id));
    }

    @PostMapping("/{id}/dispatch")
    public ResponseEntity<OrderDto> dispatch(@PathVariable String id,
                                             @Valid @RequestBody
                                             CreateOrderDispatchedDispatchOrder req) {
        return ResponseEntity.ok(dispatchOrder.dispatchOrder(id, req));
    }

    @PostMapping("/{id}/deliver")
    public ResponseEntity<OrderDto> deliver(@PathVariable String id,
                                            @Valid @RequestBody
                                            UpdateOrderDeliveredDispatchNoteDtoRequest req) {
        return ResponseEntity.ok(deliverOrder.deliverOrder(id, req));
    }

    
    @PostMapping("/{id}/cancel/pending")
    public ResponseEntity<OrderDto> cancelPending(@PathVariable String id) {
        return ResponseEntity.ok(cancelPendingOrder.cancelPendingOrder(id));
    }

    @PostMapping("/{id}/cancel/processing")
    public ResponseEntity<OrderDto> cancelProcessing(@PathVariable String id) {
        return ResponseEntity.ok(cancelProcessingOrder.cancelProcessingOrder(id));
    }

    @PostMapping("/{id}/cancel/dispatched")
    public ResponseEntity<OrderDto> cancelDispatched(@PathVariable String id) {
        return ResponseEntity.ok(cancelDispatchOrder.cancelDispatchOrder(id));
    }

    @PostMapping("/{id}/cancel/delivered")
    public ResponseEntity<OrderDto> cancelDelivered(@PathVariable String id) {
        return ResponseEntity.ok(cancelDeliveredOrder.cancelDeliveredOrder(id));
    }

    
    @PostMapping("/{id}/recalculate-total")
    public ResponseEntity<Void> recalculateTotal(@PathVariable String id) {
        recalcTotal.recalculateTotalAmount(id);
        return ResponseEntity.noContent().build();
    }
}
