package com.yasir.erp.minierp.modules.customer.infrastructure.controller;

import com.yasir.erp.minierp.modules.customer.application.dto.CustomerDto;
import com.yasir.erp.minierp.modules.customer.application.dto.request.CreateCustomerDtoRequest;
import com.yasir.erp.minierp.modules.customer.application.dto.request.UpdateCustomerDtoRequest;
import com.yasir.erp.minierp.modules.customer.application.service.query.CustomerQueryService;
import com.yasir.erp.minierp.modules.customer.domain.port.inbound.command.*;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CreateCustomerUseCase createCustomer;
    private final UpdateCustomerUseCase updateCustomer;
    private final ActivateCustomerUseCase activateCustomer;
    private final DeactivateCustomerUseCase deactivateCustomer;
    private final HardDeleteCustomerUseCase hardDeleteCustomer;
    private final CustomerQueryService customerQueries;

    public CustomerController(CreateCustomerUseCase createCustomer,
                              UpdateCustomerUseCase updateCustomer,
                              ActivateCustomerUseCase activateCustomer,
                              DeactivateCustomerUseCase deactivateCustomer,
                              HardDeleteCustomerUseCase hardDeleteCustomer,
                              CustomerQueryService customerQueries) {
        this.createCustomer = createCustomer;
        this.updateCustomer = updateCustomer;
        this.activateCustomer = activateCustomer;
        this.deactivateCustomer = deactivateCustomer;
        this.hardDeleteCustomer = hardDeleteCustomer;
        this.customerQueries = customerQueries;
    }

    
    @PostMapping
    public ResponseEntity<CustomerDto> create(@Valid @RequestBody
                                                  CreateCustomerDtoRequest createCustomerDtoRequest) {
        return ResponseEntity.ok(createCustomer.addCustomer(createCustomerDtoRequest));
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getByIdAndActive(@PathVariable UUID id,
                                                        @RequestParam(defaultValue = "true")
                                                        boolean active) {
        return ResponseEntity.ok(customerQueries.findDtoByIdAndActive(id, active));
    }

    
    @GetMapping
    public ResponseEntity<Set<CustomerDto>> listByActive(@RequestParam(defaultValue = "true")
                                                             boolean active) {
        return ResponseEntity.ok(customerQueries.findDtoAllByActive(active));
    }

    
    @GetMapping("/created-between")
    public ResponseEntity<Set<CustomerDto>> listByCreatedBetweenAndActive(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @RequestParam(defaultValue = "true") boolean active) {
        return ResponseEntity.ok(customerQueries.findDtoAllByCreatedAtBetweenAndActive
                (start, end, active));
    }

    
    @GetMapping("/by-user/{userId}")
    public ResponseEntity<Set<CustomerDto>> listByUserAndActive(@PathVariable UUID userId,
                                                                @RequestParam(defaultValue = "true")
                                                                boolean active) {
        return ResponseEntity.ok(customerQueries.findDtoAllByUserIdAndActive(userId, active));
    }

    
    @GetMapping("/by-user/{userId}/all")
    public ResponseEntity<Set<CustomerDto>> listByUserAll(@PathVariable UUID userId) {
        return ResponseEntity.ok(customerQueries.findDtoAllByUserId(userId));
    }

    
    @PutMapping
    public ResponseEntity<CustomerDto> update(@Valid @RequestBody
                                                  UpdateCustomerDtoRequest updateCustomerDtoRequest) {
        return ResponseEntity.ok(updateCustomer.updateCustomer(updateCustomerDtoRequest));
    }

    
    @PostMapping("/{id}/activate")
    public ResponseEntity<CustomerDto> activate(@PathVariable UUID id) {
        return ResponseEntity.ok(activateCustomer.activateCustomer(id));
    }

    
    @PostMapping("/{id}/deactivate")
    public ResponseEntity<CustomerDto> deactivate(@PathVariable UUID id) {
        return ResponseEntity.ok(deactivateCustomer.deactivateCustomer(id));
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> hardDelete(@PathVariable UUID id) {
        hardDeleteCustomer.hardDeleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}