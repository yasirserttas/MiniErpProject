package com.yasir.erp.minierp.modules.purchaseOrder.infrastructure.controller;

import com.yasir.erp.minierp.modules.purchaseItem.application.dto.request.CreatePurchaseItemDtoRequest;
import com.yasir.erp.minierp.modules.purchaseOrder.application.dto.PurchaseOrderDto;
import com.yasir.erp.minierp.modules.purchaseOrder.application.dto.request.CreatePurchaseOrderDtoRequest;
import com.yasir.erp.minierp.modules.purchaseOrder.application.dto.request.UpdatePurchaseOrderDtoRequest;
import com.yasir.erp.minierp.modules.purchaseOrder.domain.model.PurchaseOrderStatus;
import com.yasir.erp.minierp.modules.purchaseOrder.domain.port.inbound.command.ActivatePurchaseOrderUseCase;
import com.yasir.erp.minierp.modules.purchaseOrder.domain.port.inbound.command.AddItemToPurchaseOrderUseCase;
import com.yasir.erp.minierp.modules.purchaseOrder.domain.port.inbound.command.CreatePurchaseOrderUseCase;
import com.yasir.erp.minierp.modules.purchaseOrder.domain.port.inbound.command.DeactivatePurchaseOrderUseCase;
import com.yasir.erp.minierp.modules.purchaseOrder.domain.port.inbound.command.RecalculatePurchaseOrderTotalUseCase;
import com.yasir.erp.minierp.modules.purchaseOrder.domain.port.inbound.command.UpdatePurchaseOrderUseCase;
import com.yasir.erp.minierp.modules.purchaseOrder.domain.port.inbound.query.FindPurchaseOrderByIdUseCase;
import com.yasir.erp.minierp.modules.purchaseOrder.domain.port.inbound.query.GetTotalOrderedBySupplierUseCase;
import com.yasir.erp.minierp.modules.purchaseOrder.domain.port.inbound.query.ListPurchaseOrdersByActiveUseCase;
import com.yasir.erp.minierp.modules.purchaseOrder.domain.port.inbound.query.ListPurchaseOrdersByStatusAndActiveUseCase;
import com.yasir.erp.minierp.modules.purchaseOrder.domain.port.inbound.query.ListPurchaseOrdersByStatusAndCreatedAtBetweenAndActiveUseCase;
import com.yasir.erp.minierp.modules.purchaseOrder.domain.port.inbound.query.ListPurchaseOrdersByStatusSupplierAndActiveUseCase;
import com.yasir.erp.minierp.modules.purchaseOrder.domain.port.inbound.query.ListPurchaseOrdersByStatusUseCase;
import com.yasir.erp.minierp.modules.purchaseOrder.domain.port.inbound.query.ListPurchaseOrdersBySupplierAndActiveUseCase;
import com.yasir.erp.minierp.modules.purchaseOrder.domain.port.inbound.query.ListPurchaseOrdersBySupplierUseCase;
import com.yasir.erp.minierp.modules.purchaseOrder.domain.port.inbound.query.ListPurchaseOrdersByUpdatedAtBetweenAndActiveUseCase;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Set;

@RestController
@Validated
@RequestMapping("/api/purchase-orders")
public class PurchaseOrderController {

    private final CreatePurchaseOrderUseCase createPurchaseOrder;
    private final UpdatePurchaseOrderUseCase updatePurchaseOrder;
    private final ActivatePurchaseOrderUseCase activatePurchaseOrder;
    private final DeactivatePurchaseOrderUseCase deactivatePurchaseOrder;
    private final AddItemToPurchaseOrderUseCase addItemToPurchaseOrder;
    private final RecalculatePurchaseOrderTotalUseCase recalcTotal;

    private final FindPurchaseOrderByIdUseCase findById;
    private final GetTotalOrderedBySupplierUseCase totalBySupplier;
    private final ListPurchaseOrdersByActiveUseCase listByActive;
    private final ListPurchaseOrdersByStatusUseCase listByStatus;
    private final ListPurchaseOrdersByStatusAndActiveUseCase listByStatusAndActive;
    private final ListPurchaseOrdersBySupplierUseCase listBySupplier;
    private final ListPurchaseOrdersBySupplierAndActiveUseCase listBySupplierAndActive;
    private final ListPurchaseOrdersByUpdatedAtBetweenAndActiveUseCase listByUpdatedAtAndActive;
    private final ListPurchaseOrdersByStatusAndCreatedAtBetweenAndActiveUseCase listByStatusCreatedBetweenAndActive;
    private final ListPurchaseOrdersByStatusSupplierAndActiveUseCase listByStatusSupplierAndActive;

    public PurchaseOrderController(CreatePurchaseOrderUseCase createPurchaseOrder,
                                   UpdatePurchaseOrderUseCase updatePurchaseOrder,
                                   ActivatePurchaseOrderUseCase activatePurchaseOrder,
                                   DeactivatePurchaseOrderUseCase deactivatePurchaseOrder,
                                   AddItemToPurchaseOrderUseCase addItemToPurchaseOrder,
                                   RecalculatePurchaseOrderTotalUseCase recalcTotal,
                                   FindPurchaseOrderByIdUseCase findById,
                                   GetTotalOrderedBySupplierUseCase totalBySupplier,
                                   ListPurchaseOrdersByActiveUseCase listByActive,
                                   ListPurchaseOrdersByStatusUseCase listByStatus,
                                   ListPurchaseOrdersByStatusAndActiveUseCase listByStatusAndActive,
                                   ListPurchaseOrdersBySupplierUseCase listBySupplier,
                                   ListPurchaseOrdersBySupplierAndActiveUseCase listBySupplierAndActive,
                                   ListPurchaseOrdersByUpdatedAtBetweenAndActiveUseCase listByUpdatedAtAndActive,
                                   ListPurchaseOrdersByStatusAndCreatedAtBetweenAndActiveUseCase listByStatusCreatedBetweenAndActive,
                                   ListPurchaseOrdersByStatusSupplierAndActiveUseCase listByStatusSupplierAndActive) {
        this.createPurchaseOrder = createPurchaseOrder;
        this.updatePurchaseOrder = updatePurchaseOrder;
        this.activatePurchaseOrder = activatePurchaseOrder;
        this.deactivatePurchaseOrder = deactivatePurchaseOrder;
        this.addItemToPurchaseOrder = addItemToPurchaseOrder;
        this.recalcTotal = recalcTotal;
        this.findById = findById;
        this.totalBySupplier = totalBySupplier;
        this.listByActive = listByActive;
        this.listByStatus = listByStatus;
        this.listByStatusAndActive = listByStatusAndActive;
        this.listBySupplier = listBySupplier;
        this.listBySupplierAndActive = listBySupplierAndActive;
        this.listByUpdatedAtAndActive = listByUpdatedAtAndActive;
        this.listByStatusCreatedBetweenAndActive = listByStatusCreatedBetweenAndActive;
        this.listByStatusSupplierAndActive = listByStatusSupplierAndActive;
    }

    @PostMapping
    public ResponseEntity<PurchaseOrderDto> create(@Valid @RequestBody CreatePurchaseOrderDtoRequest req) {
        PurchaseOrderDto dto = createPurchaseOrder.addPurchaseOrder(req);
        return ResponseEntity.created(URI.create("/api/purchase-orders/" + dto.getId())).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PurchaseOrderDto> update(@PathVariable String id,
                                                   @Valid @RequestBody UpdatePurchaseOrderDtoRequest req) {
        UpdatePurchaseOrderDtoRequest fixed = new UpdatePurchaseOrderDtoRequest(id, req.getStatus());
        return ResponseEntity.ok(updatePurchaseOrder.updatePurchaseOrder(fixed));
    }

    @PostMapping("/{id}/activate")
    public ResponseEntity<PurchaseOrderDto> activate(@PathVariable String id) {
        return ResponseEntity.ok(activatePurchaseOrder.activePurchaseOrder(id));
    }

    @PostMapping("/{id}/deactivate")
    public ResponseEntity<PurchaseOrderDto> deactivate(@PathVariable String id) {
        return ResponseEntity.ok(deactivatePurchaseOrder.deactivatePurchaseOrder(id));
    }

    @PostMapping("/{id}/items")
    public ResponseEntity<PurchaseOrderDto> addItem(@PathVariable String id,
                                                    @Valid @RequestBody CreatePurchaseItemDtoRequest req) {
        CreatePurchaseItemDtoRequest fixed = new CreatePurchaseItemDtoRequest(
                req.getProductId(), id, req.getQuantity(), req.getPurchasePrice(), req.getTaxRate()
        );
        PurchaseOrderDto dto = addItemToPurchaseOrder.addItemToPurchaseOrder(id, fixed);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/{id}/recalculate-total")
    public ResponseEntity<Void> recalculateTotal(@PathVariable String id) {
        recalcTotal.recalculateTotalAmount(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseOrderDto> getById(@PathVariable String id,
                                                    @RequestParam(required = false) Boolean active) {
        return ResponseEntity.ok(findById.findById(id, active));
    }

    @GetMapping("/by-active")
    public ResponseEntity<Set<PurchaseOrderDto>> listByActive(@RequestParam Boolean active) {
        return ResponseEntity.ok(this.listByActive.listByActive(active));
    }

    @GetMapping("/by-status")
    public ResponseEntity<Set<PurchaseOrderDto>> listByStatus(@RequestParam PurchaseOrderStatus status) {
        return ResponseEntity.ok(this.listByStatus.listByStatus(status));
    }

    @GetMapping("/by-status-and-active")
    public ResponseEntity<Set<PurchaseOrderDto>> listByStatusAndActive(@RequestParam PurchaseOrderStatus status,
                                                                       @RequestParam Boolean active) {
        return ResponseEntity.ok(this.listByStatusAndActive.listByStatusAndActive(status, active));
    }

    @GetMapping("/by-supplier/{supplierId}")
    public ResponseEntity<Set<PurchaseOrderDto>> listBySupplier(@PathVariable String supplierId) {
        return ResponseEntity.ok(this.listBySupplier.listBySupplier(supplierId));
    }

    @GetMapping("/by-supplier-and-active/{supplierId}")
    public ResponseEntity<Set<PurchaseOrderDto>> listBySupplierAndActive(@PathVariable String supplierId,
                                                                         @RequestParam Boolean active) {
        return ResponseEntity.ok(this.listBySupplierAndActive.listBySupplierAndActive(supplierId, active));
    }

    @GetMapping("/by-updated-at")
    public ResponseEntity<Set<PurchaseOrderDto>> listByUpdatedAtBetweenAndActive(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @RequestParam Boolean active) {
        return ResponseEntity.ok(this.listByUpdatedAtAndActive.listByUpdatedAtBetweenAndActive(start, end, active));
    }

    @GetMapping("/by-status-created-between")
    public ResponseEntity<Set<PurchaseOrderDto>> listByStatusAndCreatedAtBetweenAndActive(
            @RequestParam PurchaseOrderStatus status,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @RequestParam Boolean active) {
        return ResponseEntity.ok(this.listByStatusCreatedBetweenAndActive
                .listByStatusAndCreatedAtBetweenAndActive(status, start, end, active));
    }

    @GetMapping("/by-status-supplier")
    public ResponseEntity<Set<PurchaseOrderDto>> listByStatusSupplierAndActive(
            @RequestParam PurchaseOrderStatus status,
            @RequestParam String supplierId,
            @RequestParam Boolean active) {
        return ResponseEntity.ok(this.listByStatusSupplierAndActive
                .listByStatusSupplierAndActive(status, supplierId, active));
    }

    @GetMapping("/total-by-supplier/{supplierId}")
    public ResponseEntity<BigDecimal> totalOrderedBySupplier(@PathVariable String supplierId) {
        return ResponseEntity.ok(this.totalBySupplier.getTotalOrderedBySupplier(supplierId));
    }
}
