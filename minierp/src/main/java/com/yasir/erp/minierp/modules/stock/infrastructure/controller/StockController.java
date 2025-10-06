package com.yasir.erp.minierp.modules.stock.infrastructure.controller;


import com.yasir.erp.minierp.modules.stock.application.dto.StockDto;
import com.yasir.erp.minierp.modules.stock.application.converter.request.CreateStockDtoRequest;
import com.yasir.erp.minierp.modules.stock.application.converter.request.UpdateStockDtoRequest;
import com.yasir.erp.minierp.modules.stock.domain.port.inbound.command.AddStockUseCase;
import com.yasir.erp.minierp.modules.stock.domain.port.inbound.command.UpdateStockUseCase;
import com.yasir.erp.minierp.modules.stock.domain.port.inbound.command.DeleteStockByIdUseCase;
import com.yasir.erp.minierp.modules.stock.domain.port.inbound.command.IncreaseStockUseCase;
import com.yasir.erp.minierp.modules.stock.domain.port.inbound.command.DecreaseStockUseCase;
import com.yasir.erp.minierp.modules.stock.domain.port.inbound.query.GetStockByIdUseCase;
import com.yasir.erp.minierp.modules.stock.domain.port.inbound.query.GetStockByProductIdUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@Validated
@RequestMapping("/api/stocks")
public class StockController {


    private final AddStockUseCase addStock;
    private final UpdateStockUseCase updateStock;
    private final DeleteStockByIdUseCase deleteStockById;
    private final IncreaseStockUseCase increaseStock;
    private final DecreaseStockUseCase decreaseStock;


    private final GetStockByIdUseCase getById;
    private final GetStockByProductIdUseCase getByProductId;

    public StockController(AddStockUseCase addStock,
                           UpdateStockUseCase updateStock,
                           DeleteStockByIdUseCase deleteStockById,
                           IncreaseStockUseCase increaseStock,
                           DecreaseStockUseCase decreaseStock,
                           GetStockByIdUseCase getById,
                           GetStockByProductIdUseCase getByProductId) {
        this.addStock = addStock;
        this.updateStock = updateStock;
        this.deleteStockById = deleteStockById;
        this.increaseStock = increaseStock;
        this.decreaseStock = decreaseStock;
        this.getById = getById;
        this.getByProductId = getByProductId;
    }

    
    @PostMapping
    public ResponseEntity<StockDto> create(@Valid @RequestBody CreateStockDtoRequest req) {
        StockDto created = addStock.addStock(req);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<StockDto> get(@PathVariable String id) {
        return ResponseEntity.ok(getById.getStockDtoById(id));
    }

    
    @GetMapping("/by-product/{productId}")
    public ResponseEntity<StockDto> getByProduct(@PathVariable String productId) {
        return ResponseEntity.ok(getByProductId.getStockDtoByProductId(productId));
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<StockDto> update(@PathVariable String id,
                                           @Valid @RequestBody UpdateStockDtoRequest req) {
        if (req.getId() == null || !id.equals(req.getId())) {
            req = new UpdateStockDtoRequest(id, req.getQuantity());
        }
        return ResponseEntity.ok(updateStock.updateStock(req));
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        deleteStockById.deleteStockById(id);
        return ResponseEntity.noContent().build();
    }

    
    
    @PostMapping("/{productId}/increase")
    public ResponseEntity<StockDto> increase(@PathVariable String productId,
                                             @RequestParam int quantity) {
        increaseStock.increaseStock(productId, quantity);
        
        return ResponseEntity.ok(getByProductId.getStockDtoByProductId(productId));
    }

    
    
    @PostMapping("/{productId}/decrease")
    public ResponseEntity<StockDto> decrease(@PathVariable String productId,
                                             @RequestParam int quantity) {
        decreaseStock.decreaseStock(productId, quantity);
        
        return ResponseEntity.ok(getByProductId.getStockDtoByProductId(productId));
    }
}
