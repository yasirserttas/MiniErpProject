package com.yasir.erp.minierp.modules.payment.infrastructure.controller;

import com.yasir.erp.minierp.modules.payment.application.dto.PaymentDto;
import com.yasir.erp.minierp.modules.payment.application.dto.request.CreatePaymentDtoRequest;
import com.yasir.erp.minierp.modules.payment.application.dto.request.UpdatePaymentDtoRequest;
import com.yasir.erp.minierp.modules.payment.domain.model.PaymentMethod;
import com.yasir.erp.minierp.modules.payment.domain.port.inbound.command.AddPaymentUseCase;
import com.yasir.erp.minierp.modules.payment.domain.port.inbound.command.DeletePaymentUseCase;
import com.yasir.erp.minierp.modules.payment.domain.port.inbound.command.UpdatePaymentUseCase;
import com.yasir.erp.minierp.modules.payment.application.service.query.PaymentQueryService;
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
@RequestMapping("/api/payments")
public class PaymentController {

    private final AddPaymentUseCase addPayment;
    private final UpdatePaymentUseCase updatePayment;
    private final DeletePaymentUseCase deletePayment;
    private final PaymentQueryService queries;

    public PaymentController(AddPaymentUseCase addPayment,
                             UpdatePaymentUseCase updatePayment,
                             DeletePaymentUseCase deletePayment,
                             PaymentQueryService queries) {
        this.addPayment = addPayment;
        this.updatePayment = updatePayment;
        this.deletePayment = deletePayment;
        this.queries = queries;
    }

    
    @PostMapping
    public ResponseEntity<PaymentDto> create(@Valid @RequestBody CreatePaymentDtoRequest req) {
        PaymentDto created = addPayment.addPayment(req);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<PaymentDto> getById(@PathVariable String id) {
        return ResponseEntity.ok(queries.findDtoById(id));
    }

    
    @GetMapping("/invoice/{invoiceId}")
    public ResponseEntity<Set<PaymentDto>> listByInvoice(@PathVariable String invoiceId) {
        return ResponseEntity.ok(queries.findDtoAllByInvoiceId(invoiceId));
    }

    
    @GetMapping("/invoice/{invoiceId}/method/{method}")
    public ResponseEntity<Set<PaymentDto>> listByInvoiceAndMethod(@PathVariable String invoiceId,
                                                                  @PathVariable PaymentMethod method) {
        return ResponseEntity.ok(queries.findDtoAllByInvoiceIdAndMethod(invoiceId, method));
    }

    
    @GetMapping("/method/{method}")
    public ResponseEntity<Set<PaymentDto>> listByMethod(@PathVariable PaymentMethod method) {
        return ResponseEntity.ok(queries.findDtoAllByMethod(method));
    }

    
    @GetMapping("/method/{method}/paid-range")
    public ResponseEntity<Set<PaymentDto>> listByMethodAndPaidRange(
            @PathVariable PaymentMethod method,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(queries.findDtoAllByMethodAndPaidAtBetween(method, start, end));
    }

    
    @GetMapping("/paid-range")
    public ResponseEntity<Set<PaymentDto>> listByPaidAtRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(queries.findDtoAllByPaidAtBetween(start, end));
    }

    
    @GetMapping("/due-range")
    public ResponseEntity<Set<PaymentDto>> listByDueDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(queries.findDtoAllByDueDateBetween(start, end));
    }

    
    @GetMapping("/created-range")
    public ResponseEntity<Set<PaymentDto>> listByCreatedRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(queries.findDtoAllByCreatedAtBetween(start, end));
    }

    
    @GetMapping("/updated-range")
    public ResponseEntity<Set<PaymentDto>> listByUpdatedRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(queries.findDtoAllByUpdatedAtBetween(start, end));
    }

    
    @GetMapping("/search")
    public ResponseEntity<Set<PaymentDto>> searchByNote(@RequestParam String note) {
        return ResponseEntity.ok(queries.findDtoAllByNoteContainingIgnoreCase(note));
    }

    
    @GetMapping("/amount/min")
    public ResponseEntity<Set<PaymentDto>> listByAmountMin(@RequestParam BigDecimal amount) {
        return ResponseEntity.ok(queries.findDtoAllByAmountGreaterThanEqual(amount));
    }

    @GetMapping("/amount/max")
    public ResponseEntity<Set<PaymentDto>> listByAmountMax(@RequestParam BigDecimal amount) {
        return ResponseEntity.ok(queries.findDtoAllByAmountLessThanEqual(amount));
    }

    @GetMapping("/amount/range")
    public ResponseEntity<Set<PaymentDto>> listByAmountRange(@RequestParam BigDecimal min,
                                                             @RequestParam BigDecimal max) {
        return ResponseEntity.ok(queries.findDtoAllByAmountBetween(min, max));
    }

    
    @GetMapping("/customers/{customerId}/total-paid")
    public ResponseEntity<BigDecimal> totalPaidByCustomer(@PathVariable UUID customerId) {
        return ResponseEntity.ok(queries.getTotalPaidByCustomer(customerId));
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<PaymentDto> update(@PathVariable String id,
                                             @Valid @RequestBody UpdatePaymentDtoRequest
                                                     updatePaymentDtoRequest) {

        UpdatePaymentDtoRequest fixed = new UpdatePaymentDtoRequest(
                id,
                updatePaymentDtoRequest.getAmount(),
                updatePaymentDtoRequest.getMethod(),
                updatePaymentDtoRequest.getPaidAt(),
                updatePaymentDtoRequest.getIssueDate(),
                updatePaymentDtoRequest.getDueDate(),
                updatePaymentDtoRequest.getNote()
        );
        return ResponseEntity.ok(updatePayment.updatePayment(fixed));
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        deletePayment.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
