package com.yasir.erp.minierp.modules.invoice.infrastructure.controller;

import com.yasir.erp.minierp.modules.invoice.application.dto.InvoiceDto;
import com.yasir.erp.minierp.modules.invoice.application.dto.request.CreateInvoiceDtoRequest;
import com.yasir.erp.minierp.modules.invoice.application.dto.request.UpdateInvoiceDtoRequest;
import com.yasir.erp.minierp.modules.invoice.application.service.query.InvoiceQueryService;
import com.yasir.erp.minierp.modules.invoice.domain.port.inbound.command.*;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final CreateInvoiceUseCase createUseCase;
    private final UpdateInvoiceUseCase updateUseCase;
    private final ActivateInvoiceUseCase activateUseCase;
    private final DeactivateInvoiceUseCase deactivateUseCase;
    private final HardDeleteInvoiceUseCase hardDeleteUseCase;
    private final InvoiceQueryService queries;

    public InvoiceController(CreateInvoiceUseCase createUseCase,
                             UpdateInvoiceUseCase updateUseCase,
                             ActivateInvoiceUseCase activateUseCase,
                             DeactivateInvoiceUseCase deactivateUseCase,
                             HardDeleteInvoiceUseCase hardDeleteUseCase,
                             InvoiceQueryService queries) {
        this.createUseCase = createUseCase;
        this.updateUseCase = updateUseCase;
        this.activateUseCase = activateUseCase;
        this.deactivateUseCase = deactivateUseCase;
        this.hardDeleteUseCase = hardDeleteUseCase;
        this.queries = queries;
    }

        @PostMapping
    public ResponseEntity<InvoiceDto> create(@Valid @RequestBody CreateInvoiceDtoRequest
                                                     createInvoiceDtoRequest) {
        InvoiceDto created = createUseCase.addInvoice(createInvoiceDtoRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

        @GetMapping("/{id}")
    public ResponseEntity<InvoiceDto> getById(@PathVariable String id,
                                              @RequestParam(defaultValue = "true") Boolean active) {
        return ResponseEntity.ok(queries.findDtoByIdAndActive(id, active));
    }

        @GetMapping("/by-number/{number}")
    public ResponseEntity<InvoiceDto> getByNumber(@PathVariable String number,
                                                  @RequestParam(defaultValue = "true") Boolean active) {
        return ResponseEntity.ok(queries.findDtoByInvoiceNumberAndActive(number, active));
    }

        @GetMapping("/by-order/{orderId}")
    public ResponseEntity<InvoiceDto> getByOrder(@PathVariable String orderId,
                                                 @RequestParam(defaultValue = "true") Boolean active) {
        return ResponseEntity.ok(queries.findDtoByOrder_IdAndActive(orderId, active));
    }

        @GetMapping
    public ResponseEntity<Set<InvoiceDto>> listByActive
    (@RequestParam(defaultValue = "true") Boolean active) {
        return active ? ResponseEntity.ok(queries.findDtoAllByActiveTrue())
                : ResponseEntity.ok(queries.findDtoAllByActiveFalse());
    }

        @GetMapping("/min-amount")
    public ResponseEntity<Set<InvoiceDto>> listByMinAmount(
            @RequestParam BigDecimal amount,
            @RequestParam(defaultValue = "true") Boolean active) {
        return ResponseEntity.ok(queries.findDtoAllByFinalAmountGreaterThanEqualAndActive
                (amount, active));
    }

            @GetMapping("/created-between")
    public ResponseEntity<Set<InvoiceDto>> listByCreatedBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @RequestParam(defaultValue = "true") Boolean active) {
        return ResponseEntity.ok(queries.findDtoAllByCreatedAtBetweenAndActive(start, end, active));
    }

            @GetMapping("/by-customer/{customerId}/created-between")
    public ResponseEntity<Set<InvoiceDto>> listByCustomerAndCreatedBetween(
            @PathVariable UUID customerId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @RequestParam(defaultValue = "true") Boolean active) {
        return ResponseEntity.ok(
                queries.findDtoAllByCustomer_IdAndCreatedAtBetweenAndActive
                        (customerId, start, end, active)
        );
    }

            @GetMapping("/by-customer/{customerId}")
    public ResponseEntity<Set<InvoiceDto>> listByCustomer(
            @PathVariable UUID customerId,
            @RequestParam(defaultValue = "true") Boolean active) {
        return ResponseEntity.ok(queries.findDtoAllByCustomer_IdAndActiveIs(customerId, active));
    }

            @GetMapping("/by-issued")
    public ResponseEntity<Set<InvoiceDto>> listByIssuedBy(
            @RequestParam String issuedBy,
            @RequestParam(defaultValue = "true") Boolean active) {
        return ResponseEntity.ok(queries.findDtoAllByIssuedByAndActive(issuedBy, active));
    }

            @GetMapping("/by-received")
    public ResponseEntity<Set<InvoiceDto>> listByReceivedBy(
            @RequestParam String receivedBy,
            @RequestParam(defaultValue = "true") Boolean active) {
        return ResponseEntity.ok(queries.findDtoAllByReceivedByAndActive(receivedBy, active));
    }

            @GetMapping("/by-user/{userId}")
    public ResponseEntity<Set<InvoiceDto>> listByUser(
            @PathVariable UUID userId,
            @RequestParam(defaultValue = "true") Boolean active) {
        return ResponseEntity.ok(queries.findDtoAllByUser_IdAndActive(userId, active));
    }

            @PutMapping
    public ResponseEntity<InvoiceDto> update(@Valid @RequestBody UpdateInvoiceDtoRequest
                                                     updateInvoiceDtoRequest) {
        return ResponseEntity.ok(updateUseCase.updateInvoice(updateInvoiceDtoRequest));
    }

            @PostMapping("/{id}/activate")
    public ResponseEntity<InvoiceDto> activate(@PathVariable String id) {
        return ResponseEntity.ok(activateUseCase.activeInvoice(id));
    }

            @PostMapping("/{id}/deactivate")
    public ResponseEntity<InvoiceDto> deactivate(@PathVariable String id) {
        return ResponseEntity.ok(deactivateUseCase.deactivateInvoice(id));
    }

            @DeleteMapping("/{id}")
    public ResponseEntity<Void> hardDelete(@PathVariable String id) {
        hardDeleteUseCase.hardDelete(id);
        return ResponseEntity.noContent().build();
    }
}
