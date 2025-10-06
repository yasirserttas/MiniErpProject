package com.yasir.erp.minierp.modules.receipt.infrastructure.controller;

import com.yasir.erp.minierp.modules.receipt.application.dto.ReceiptDto;
import com.yasir.erp.minierp.modules.receipt.application.dto.request.CreateReceiptDtoRequest;
import com.yasir.erp.minierp.modules.receipt.application.dto.request.UpdateReceiptDtoRequest;
import com.yasir.erp.minierp.modules.receipt.domain.port.inbound.command.*;
import com.yasir.erp.minierp.modules.receipt.domain.port.inbound.query.*;
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
@RequestMapping("/api/receipts")
public class ReceiptController {

    private final CreateReceiptUseCase createReceipt;
    private final UpdateReceiptUseCase updateReceipt;
    private final ActivateReceiptUseCase activateReceipt;
    private final DeactivateReceiptUseCase deactivateReceipt;
    private final HardDeleteReceiptUseCase hardDeleteReceipt;

    private final FindReceiptByIdUseCase findById;
    private final FindReceiptByNumberUseCase findByNumber;
    private final FindReceiptByPaymentUseCase findByPaymentSingle;

    private final ListReceiptsUseCase listAll;
    private final ListReceiptsByActiveUseCase listByActive;
    private final ListReceiptsByDateRangeUseCase listByDateRange;
    private final ListReceiptsByIssuedByAndDateUseCase listByIssuerAndDate;
    private final ListReceiptsByIssuedByUseCase listByIssuer;
    private final ListReceiptsByPaymentUseCase listByPayment;
    private final ListReceiptsByReceivedByUseCase listByReceiver;

    public ReceiptController(CreateReceiptUseCase createReceipt,
                             UpdateReceiptUseCase updateReceipt,
                             ActivateReceiptUseCase activateReceipt,
                             DeactivateReceiptUseCase deactivateReceipt,
                             HardDeleteReceiptUseCase hardDeleteReceipt,
                             FindReceiptByIdUseCase findById,
                             FindReceiptByNumberUseCase findByNumber,
                             FindReceiptByPaymentUseCase findByPaymentSingle,
                             ListReceiptsUseCase listAll,
                             ListReceiptsByActiveUseCase listByActive,
                             ListReceiptsByDateRangeUseCase listByDateRange,
                             ListReceiptsByIssuedByAndDateUseCase listByIssuerAndDate,
                             ListReceiptsByIssuedByUseCase listByIssuer,
                             ListReceiptsByPaymentUseCase listByPayment,
                             ListReceiptsByReceivedByUseCase listByReceiver) {
        this.createReceipt = createReceipt;
        this.updateReceipt = updateReceipt;
        this.activateReceipt = activateReceipt;
        this.deactivateReceipt = deactivateReceipt;
        this.hardDeleteReceipt = hardDeleteReceipt;
        this.findById = findById;
        this.findByNumber = findByNumber;
        this.findByPaymentSingle = findByPaymentSingle;
        this.listAll = listAll;
        this.listByActive = listByActive;
        this.listByDateRange = listByDateRange;
        this.listByIssuerAndDate = listByIssuerAndDate;
        this.listByIssuer = listByIssuer;
        this.listByPayment = listByPayment;
        this.listByReceiver = listByReceiver;
    }

    @PostMapping
    public ResponseEntity<ReceiptDto> create(@Valid @RequestBody CreateReceiptDtoRequest req) {
        ReceiptDto dto = createReceipt.createReceipt(req);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dto.getId())
                .toUri();
        return ResponseEntity.created(location).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReceiptDto> update(@PathVariable String id,
                                             @Valid @RequestBody UpdateReceiptDtoRequest req) {
        UpdateReceiptDtoRequest fixed = new UpdateReceiptDtoRequest(
                id, req.getIssuedBy(), req.getReceivedBy(), req.getReceiptDate()
        );
        return ResponseEntity.ok(updateReceipt.updateReceipt(fixed));
    }

    @PostMapping("/{id}/activate")
    public ResponseEntity<ReceiptDto> activate(@PathVariable String id) {
        return ResponseEntity.ok(activateReceipt.activateReceipt(id));
    }

    @PostMapping("/{id}/deactivate")
    public ResponseEntity<ReceiptDto> deactivate(@PathVariable String id) {
        return ResponseEntity.ok(deactivateReceipt.deactivateReceipt(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> hardDelete(@PathVariable String id) {
        hardDeleteReceipt.hardDeleteReceipt(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReceiptDto> getById(@PathVariable String id,
                                              @RequestParam(defaultValue = "true") boolean active) {
        return ResponseEntity.ok(findById.findById(id, active));
    }

    @GetMapping("/by-number/{number}")
    public ResponseEntity<ReceiptDto> getByNumber(@PathVariable String number,
                                                  @RequestParam(defaultValue = "true") boolean active) {
        return ResponseEntity.ok(findByNumber.findByNumber(number, active));
    }

    @GetMapping("/by-payment/{paymentId}")
    public ResponseEntity<ReceiptDto> getByPayment(@PathVariable String paymentId,
                                                   @RequestParam(defaultValue = "true") boolean active) {
        return ResponseEntity.ok(findByPaymentSingle.findByPaymentId(paymentId, active));
    }

    @GetMapping
    public ResponseEntity<Set<ReceiptDto>> list(
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @RequestParam(required = false) String issuedBy,
            @RequestParam(required = false) String receivedBy,
            @RequestParam(required = false) String paymentId
    ) {
        if (active != null) {
            return ResponseEntity.ok(listByActive.findAllByActiveStatus(active));
        }
        if (start != null && end != null) {
            return ResponseEntity.ok(listByDateRange.findByDateRangeAndActiveStatus(start, end, true));
        }
        if (issuedBy != null && !issuedBy.isBlank() && start != null && end != null) {
            return ResponseEntity.ok(listByIssuerAndDate.findAllByIssuerAndDateRangeAndStatus(issuedBy, start, end, true));
        }
        if (issuedBy != null && !issuedBy.isBlank()) {
            return ResponseEntity.ok(listByIssuer.findAllByIssuerAndStatus(issuedBy, true));
        }
        if (receivedBy != null && !receivedBy.isBlank()) {
            return ResponseEntity.ok(listByReceiver.findAllByReceiverAndStatus(receivedBy, true));
        }
        if (paymentId != null && !paymentId.isBlank()) {
            return ResponseEntity.ok(listByPayment.findAllByPaymentIdAndStatus(paymentId, true));
        }
        return ResponseEntity.ok(listAll.findAll());
    }
}
