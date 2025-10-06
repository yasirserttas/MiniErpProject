package com.yasir.erp.minierp.modules.orderItem.infrastructure.controller;

import com.yasir.erp.minierp.modules.orderItem.application.dto.OrderItemDto;
import com.yasir.erp.minierp.modules.orderItem.application.dto.request.CreateOrderItemDtoRequest;
import com.yasir.erp.minierp.modules.orderItem.application.dto.request.UpdateOrderItemDtoRequest;
import com.yasir.erp.minierp.modules.orderItem.application.service.query.OrderItemQueryService;
import com.yasir.erp.minierp.modules.orderItem.domain.port.inbound.command.CreateOrderItemUseCase;
import com.yasir.erp.minierp.modules.orderItem.domain.port.inbound.command.DeleteOrderItemUseCase;
import com.yasir.erp.minierp.modules.orderItem.domain.port.inbound.command.UpdateOrderItemUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Set;

@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {

    private final CreateOrderItemUseCase createOrderItem;
    private final UpdateOrderItemUseCase updateOrderItem;
    private final DeleteOrderItemUseCase deleteOrderItem;
    private final OrderItemQueryService queries;

    public OrderItemController(CreateOrderItemUseCase createOrderItem,
                               UpdateOrderItemUseCase updateOrderItem,
                               DeleteOrderItemUseCase deleteOrderItem,
                               OrderItemQueryService queries) {
        this.createOrderItem = createOrderItem;
        this.updateOrderItem = updateOrderItem;
        this.deleteOrderItem = deleteOrderItem;
        this.queries = queries;
    }

    
    @PostMapping
    public ResponseEntity<OrderItemDto> create(@Valid @RequestBody
                                                   CreateOrderItemDtoRequest createOrderItemDtoRequest) {
        OrderItemDto created = createOrderItem.createOrderItem(createOrderItemDtoRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PostMapping("/orders/{orderId}")
    public ResponseEntity<OrderItemDto> createForOrder(@PathVariable String orderId,
                                                       @Valid @RequestBody CreateOrderItemDtoRequest
                                                               createOrderItemDtoRequest) {
        CreateOrderItemDtoRequest fixed =
                new CreateOrderItemDtoRequest(orderId, createOrderItemDtoRequest.getProductId(),
                        createOrderItemDtoRequest.getQuantity(),
                        createOrderItemDtoRequest.getUnitPrice());
        OrderItemDto created = createOrderItem.createOrderItem(fixed);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<OrderItemDto> getById(@PathVariable String id) {
        return ResponseEntity.ok(queries.findOrderItemDtoById(id));
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<Set<OrderItemDto>> listByOrder(@PathVariable String orderId) {
        return ResponseEntity.ok(queries.listOrderItemSetDtoByOrderId(orderId));
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<OrderItemDto> update(@PathVariable String id,
                                               @Valid @RequestBody UpdateOrderItemDtoRequest
                                                       updateOrderItemDtoRequest) {
        UpdateOrderItemDtoRequest fixed = new UpdateOrderItemDtoRequest(
                id,
                updateOrderItemDtoRequest.getQuantity(),
                updateOrderItemDtoRequest.getUnitPrice()
        );
        return ResponseEntity.ok(updateOrderItem.updateOrderItem(fixed));
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        deleteOrderItem.deleteOrderItem(id);
        return ResponseEntity.noContent().build();
    }
}
