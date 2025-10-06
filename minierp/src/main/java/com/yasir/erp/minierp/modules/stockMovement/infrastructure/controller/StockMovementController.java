package com.yasir.erp.minierp.modules.stockMovement.infrastructure.controller;

import com.yasir.erp.minierp.modules.stockMovement.application.dto.StockMovementDto;
import com.yasir.erp.minierp.modules.stockMovement.application.dto.request.CreateStockMovementDtoRequest;
import com.yasir.erp.minierp.modules.stockMovement.application.dto.request.UpdateStockMovementDtoRequest;
import com.yasir.erp.minierp.modules.stockMovement.domain.model.StockMovementType;
import com.yasir.erp.minierp.modules.stockMovement.domain.port.inbound.command.AddStockMovementUseCase;
import com.yasir.erp.minierp.modules.stockMovement.domain.port.inbound.command.DeleteStockMovementByIdUseCase;
import com.yasir.erp.minierp.modules.stockMovement.domain.port.inbound.command.UpdateStockMovementUseCase;
import com.yasir.erp.minierp.modules.stockMovement.domain.port.inbound.query.GetStockMovementByIdUseCase;
import com.yasir.erp.minierp.modules.stockMovement.domain.port.inbound.query.ListAllStockMovementsUseCase;
import com.yasir.erp.minierp.modules.stockMovement.domain.port.inbound.query.ListStockMovementsByDateBetweenUseCase;
import com.yasir.erp.minierp.modules.stockMovement.domain.port.inbound.query.ListStockMovementsByMovementTypeAndDateBetweenUseCase;
import com.yasir.erp.minierp.modules.stockMovement.domain.port.inbound.query.ListStockMovementsByMovementTypeUseCase;
import com.yasir.erp.minierp.modules.stockMovement.domain.port.inbound.query.ListStockMovementsByProductIdAndDateBetweenUseCase;
import com.yasir.erp.minierp.modules.stockMovement.domain.port.inbound.query.ListStockMovementsByProductIdAndMovementTypeUseCase;
import com.yasir.erp.minierp.modules.stockMovement.domain.port.inbound.query.ListStockMovementsByProductIdDateBetweenAndMovementTypeUseCase;
import com.yasir.erp.minierp.modules.stockMovement.domain.port.inbound.query.ListStockMovementsByProductIdUseCase;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Set;

@RestController
@Validated
@RequestMapping("/api/stock-movements")
public class StockMovementController {
    private final AddStockMovementUseCase addStockMovement;
    private final UpdateStockMovementUseCase updateStockMovement;
    private final DeleteStockMovementByIdUseCase deleteStockMovementById;
    private final GetStockMovementByIdUseCase getById;
    private final ListAllStockMovementsUseCase listAll;
    private final ListStockMovementsByProductIdUseCase listByProductId;
    private final ListStockMovementsByDateBetweenUseCase listByDateBetween;
    private final ListStockMovementsByMovementTypeUseCase listByType;
    private final ListStockMovementsByMovementTypeAndDateBetweenUseCase listByTypeAndDate;
    private final ListStockMovementsByProductIdAndDateBetweenUseCase listByProductAndDate;
    private final ListStockMovementsByProductIdAndMovementTypeUseCase listByProductAndType;
    private final ListStockMovementsByProductIdDateBetweenAndMovementTypeUseCase listByProductDateType;

    public StockMovementController(AddStockMovementUseCase addStockMovement,
                                   UpdateStockMovementUseCase updateStockMovement,
                                   DeleteStockMovementByIdUseCase deleteStockMovementById,
                                   GetStockMovementByIdUseCase getById,
                                   ListAllStockMovementsUseCase listAll,
                                   ListStockMovementsByProductIdUseCase listByProductId,
                                   ListStockMovementsByDateBetweenUseCase listByDateBetween,
                                   ListStockMovementsByMovementTypeUseCase listByType,
                                   ListStockMovementsByMovementTypeAndDateBetweenUseCase listByTypeAndDate,
                                   ListStockMovementsByProductIdAndDateBetweenUseCase listByProductAndDate,
                                   ListStockMovementsByProductIdAndMovementTypeUseCase listByProductAndType,
                                   ListStockMovementsByProductIdDateBetweenAndMovementTypeUseCase listByProductDateType) {
        this.addStockMovement = addStockMovement;
        this.updateStockMovement = updateStockMovement;
        this.deleteStockMovementById = deleteStockMovementById;
        this.getById = getById;
        this.listAll = listAll;
        this.listByProductId = listByProductId;
        this.listByDateBetween = listByDateBetween;
        this.listByType = listByType;
        this.listByTypeAndDate = listByTypeAndDate;
        this.listByProductAndDate = listByProductAndDate;
        this.listByProductAndType = listByProductAndType;
        this.listByProductDateType = listByProductDateType;
    }

    @PostMapping
    public ResponseEntity<StockMovementDto> create(@Valid @RequestBody CreateStockMovementDtoRequest req) {
        StockMovementDto created = addStockMovement.addStockMovement(req);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockMovementDto> getById(@PathVariable String id) {
        return ResponseEntity.ok(getById.getStockMovementDtoById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StockMovementDto> update(@PathVariable String id,
                                                   @Valid @RequestBody UpdateStockMovementDtoRequest req) {
        if (req.getId() == null || !id.equals(req.getId())) {
            req = new UpdateStockMovementDtoRequest(
                    id,
                    req.getQuantity(),
                    req.getMovementType(),
                    req.getDescription()
            );
        }
        return ResponseEntity.ok(updateStockMovement.updateStockMovement(req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        deleteStockMovementById.deleteStockMovementById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Set<StockMovementDto>> list(
            @RequestParam(required = false) String productId,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @RequestParam(required = false) StockMovementType movementType
    ) {
        Set<StockMovementDto> result;

        boolean hasProduct = productId != null && !productId.isBlank();
        boolean hasDates = start != null && end != null;
        boolean hasType = movementType != null;

        if (hasProduct && hasDates && hasType) {
            result = listByProductDateType.findAllDtoByProductIdAndDateBetweenAndMovementType(productId, start, end, movementType);
        } else if (hasProduct && hasDates) {
            result = listByProductAndDate.findAllDtoByProductIdAndDateBetween(productId, start, end);
        } else if (hasProduct && hasType) {
            result = listByProductAndType.findAllDtoByProductIdAndMovementType(productId, movementType);
        } else if (hasProduct) {
            result = listByProductId.findAllDtoByProductId(productId);
        } else if (hasDates && hasType) {
            result = listByTypeAndDate.findAllDtoByMovementTypeAndDateBetween(movementType, start, end);
        } else if (hasDates) {
            result = listByDateBetween.findAllDtoByDateBetween(start, end);
        } else if (hasType) {
            result = listByType.findAllDtoByMovementType(movementType);
        } else {
            result = listAll.findByDtoAll();
        }

        return ResponseEntity.ok(result);
    }
}
