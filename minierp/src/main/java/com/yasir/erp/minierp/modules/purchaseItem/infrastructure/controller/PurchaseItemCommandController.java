package com.yasir.erp.minierp.modules.purchaseItem.infrastructure.controller;


import com.yasir.erp.minierp.modules.purchaseItem.application.dto.PurchaseItemDto;
import com.yasir.erp.minierp.modules.purchaseItem.application.dto.request.CreatePurchaseItemDtoRequest;
import com.yasir.erp.minierp.modules.purchaseItem.application.dto.request.UpdatePurchaseItemDtoRequest;
import com.yasir.erp.minierp.modules.purchaseItem.domain.port.inbound.command.CreatePurchaseItemUseCase;
import com.yasir.erp.minierp.modules.purchaseItem.domain.port.inbound.command.UpdatePurchaseItemUseCase;
import com.yasir.erp.minierp.modules.purchaseItem.domain.port.inbound.command.DeletePurchaseItemUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@Validated
@RequestMapping("/api/purchase-items")
public class PurchaseItemCommandController {

    private final CreatePurchaseItemUseCase createPurchaseItem;
    private final UpdatePurchaseItemUseCase updatePurchaseItem;
    private final DeletePurchaseItemUseCase deletePurchaseItem;

    public PurchaseItemCommandController(CreatePurchaseItemUseCase createPurchaseItem,
                                         UpdatePurchaseItemUseCase updatePurchaseItem,
                                         DeletePurchaseItemUseCase deletePurchaseItem) {
        this.createPurchaseItem = createPurchaseItem;
        this.updatePurchaseItem = updatePurchaseItem;
        this.deletePurchaseItem = deletePurchaseItem;
    }

    @PostMapping
    public ResponseEntity<PurchaseItemDto> create(@Valid @RequestBody CreatePurchaseItemDtoRequest req) {
        PurchaseItemDto dto = createPurchaseItem.createPurchaseItem(req);
        return ResponseEntity
                .created(URI.create("/api/purchase-items/" + dto.getId()))
                .body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PurchaseItemDto> update(@PathVariable String id,
                                                  @Valid @RequestBody UpdatePurchaseItemDtoRequest req) {

        UpdatePurchaseItemDtoRequest fixed = new UpdatePurchaseItemDtoRequest(
                id,
                req.getQuantity(),
                req.getPurchasePrice(),
                req.getTaxRate()
        );
        return ResponseEntity.ok(updatePurchaseItem.updatePurchaseItem(fixed));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        deletePurchaseItem.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
