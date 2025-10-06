package com.yasir.erp.minierp.modules.supplier.infrastructure.controller;

import com.yasir.erp.minierp.modules.supplier.application.dto.SupplierDto;
import com.yasir.erp.minierp.modules.supplier.application.dto.request.CreateSupplierDtoRequest;
import com.yasir.erp.minierp.modules.supplier.application.dto.request.UpdateSupplierDtoRequest;
import com.yasir.erp.minierp.modules.supplier.domain.port.inbound.command.ActivateSupplierUseCase;
import com.yasir.erp.minierp.modules.supplier.domain.port.inbound.command.AddSupplierUseCase;
import com.yasir.erp.minierp.modules.supplier.domain.port.inbound.command.DeactivateSupplierUseCase;
import com.yasir.erp.minierp.modules.supplier.domain.port.inbound.command.HardDeleteSupplierByIdUseCase;
import com.yasir.erp.minierp.modules.supplier.domain.port.inbound.command.UpdateSupplierUseCase;
import com.yasir.erp.minierp.modules.supplier.domain.port.inbound.query.FindSupplierByIdAndActiveUseCase;
import com.yasir.erp.minierp.modules.supplier.domain.port.inbound.query.FindSupplierByTaxNumberAndActiveUseCase;
import com.yasir.erp.minierp.modules.supplier.domain.port.inbound.query.ListSuppliersByActiveUseCase;
import com.yasir.erp.minierp.modules.supplier.domain.port.inbound.query.ListSuppliersByNameContainingIgnoreCaseAndActiveUseCase;
import com.yasir.erp.minierp.modules.supplier.domain.port.inbound.query.ListSuppliersByUpdateAtBetweenAndActiveUseCase;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Set;

@RestController
@Validated
@RequestMapping("/api/suppliers")
public class SupplierController {

    private final AddSupplierUseCase addSupplier;
    private final UpdateSupplierUseCase updateSupplier;
    private final ActivateSupplierUseCase activateSupplier;
    private final DeactivateSupplierUseCase deactivateSupplier;
    private final HardDeleteSupplierByIdUseCase hardDeleteSupplier;

    private final FindSupplierByIdAndActiveUseCase findByIdAndActive;
    private final FindSupplierByTaxNumberAndActiveUseCase findByTaxAndActive;
    private final ListSuppliersByActiveUseCase listByActive;
    private final ListSuppliersByNameContainingIgnoreCaseAndActiveUseCase listByNameAndActive;
    private final ListSuppliersByUpdateAtBetweenAndActiveUseCase listByUpdatedAtAndActive;

    public SupplierController(AddSupplierUseCase addSupplier,
                              UpdateSupplierUseCase updateSupplier,
                              ActivateSupplierUseCase activateSupplier,
                              DeactivateSupplierUseCase deactivateSupplier,
                              HardDeleteSupplierByIdUseCase hardDeleteSupplier,
                              FindSupplierByIdAndActiveUseCase findByIdAndActive,
                              FindSupplierByTaxNumberAndActiveUseCase findByTaxAndActive,
                              ListSuppliersByActiveUseCase listByActive,
                              ListSuppliersByNameContainingIgnoreCaseAndActiveUseCase listByNameAndActive,
                              ListSuppliersByUpdateAtBetweenAndActiveUseCase listByUpdatedAtAndActive) {
        this.addSupplier = addSupplier;
        this.updateSupplier = updateSupplier;
        this.activateSupplier = activateSupplier;
        this.deactivateSupplier = deactivateSupplier;
        this.hardDeleteSupplier = hardDeleteSupplier;
        this.findByIdAndActive = findByIdAndActive;
        this.findByTaxAndActive = findByTaxAndActive;
        this.listByActive = listByActive;
        this.listByNameAndActive = listByNameAndActive;
        this.listByUpdatedAtAndActive = listByUpdatedAtAndActive;
    }

    
    @PostMapping
    public ResponseEntity<SupplierDto> create(@Valid @RequestBody CreateSupplierDtoRequest req) {
        SupplierDto dto = addSupplier.addSupplier(req);
        return ResponseEntity.created(URI.create("/api/suppliers/" + dto.getId())).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplierDto> update(@PathVariable String id,
                                              @Valid @RequestBody UpdateSupplierDtoRequest req) {
        UpdateSupplierDtoRequest fixed = new UpdateSupplierDtoRequest(
                id, req.getName(), req.getTaxNumber(), req.getPhone(), req.getEmail()
        );
        return ResponseEntity.ok(updateSupplier.updateSupplier(fixed));
    }

    @PostMapping("/{id}/activate")
    public ResponseEntity<SupplierDto> activate(@PathVariable String id) {
        return ResponseEntity.ok(activateSupplier.activateSupplier(id));
    }

    @PostMapping("/{id}/deactivate")
    public ResponseEntity<SupplierDto> deactivate(@PathVariable String id) {
        return ResponseEntity.ok(deactivateSupplier.deactivateSupplier(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> hardDelete(@PathVariable String id) {
        hardDeleteSupplier.hardDeleteSupplierById(id);
        return ResponseEntity.noContent().build();
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<SupplierDto> getById(@PathVariable String id,
                                               @RequestParam(defaultValue = "true") Boolean active) {
        return ResponseEntity.ok(findByIdAndActive.findDtoByIdAndActive(id, active));
    }

    @GetMapping("/by-tax-number/{taxNumber}")
    public ResponseEntity<SupplierDto> getByTaxNumber(@PathVariable String taxNumber,
                                                      @RequestParam(defaultValue = "true") Boolean active) {
        return ResponseEntity.ok(findByTaxAndActive.findDtoByTaxNumberAndActive(taxNumber, active));
    }

    @GetMapping
    public ResponseEntity<Set<SupplierDto>> list(@RequestParam(defaultValue = "true") Boolean active) {
        return ResponseEntity.ok(listByActive.findDtoAllByActive(active));
    }

    @GetMapping("/search")
    public ResponseEntity<Set<SupplierDto>> searchByName(@RequestParam String name,
                                                         @RequestParam(defaultValue = "true") Boolean active) {
        return ResponseEntity.ok(
                listByNameAndActive.findDtoAllByNameContainingIgnoreCaseAndActive(name, active)
        );
    }

    @GetMapping("/by-updated-at")
    public ResponseEntity<Set<SupplierDto>> byUpdatedAt(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @RequestParam(defaultValue = "true") Boolean active) {
        return ResponseEntity.ok(
                listByUpdatedAtAndActive.findDtoAllByUpdateAtBetweenAndActive(start, end, active)
        );
    }
}
