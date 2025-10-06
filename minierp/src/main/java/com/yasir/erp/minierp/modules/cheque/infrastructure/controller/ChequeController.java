package com.yasir.erp.minierp.modules.cheque.infrastructure.controller;

import com.yasir.erp.minierp.modules.cheque.application.dto.ChequeDto;
import com.yasir.erp.minierp.modules.cheque.application.dto.request.CreateChequeDtoRequest;
import com.yasir.erp.minierp.modules.cheque.application.dto.request.UpdateChequeDtoRequest;
import com.yasir.erp.minierp.modules.cheque.application.service.query.ChequeQueryService;
import com.yasir.erp.minierp.modules.cheque.domain.model.ChequeStatus;
import com.yasir.erp.minierp.modules.cheque.domain.port.inbound.command.*;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Set;

@RestController
@RequestMapping("/api/cheques")
public class ChequeController {

    private final AddChequeUseCase addCheque;
    private final UpdateChequeUseCase updateCheque;
    private final SoftDeleteChequeUseCase softDeleteCheque;
    private final HardDeleteChequeUseCase hardDeleteCheque;
    private final CancelChequeUseCase cancelCheque;
    private final ReactivateChequeUseCase reactivateCheque;
    private final MarkChequeAsCollectedUseCase markAsCollected;
    private final MarkChequeAsPaidUseCase markAsPaid;
    private final MarkOverdueChequesIfNecessaryUseCase markOverdueBatch;
    private final ChequeQueryService chequeQueries;

    public ChequeController(AddChequeUseCase addCheque,
                            UpdateChequeUseCase updateCheque,
                            SoftDeleteChequeUseCase softDeleteCheque,
                            HardDeleteChequeUseCase hardDeleteCheque,
                            CancelChequeUseCase cancelCheque,
                            ReactivateChequeUseCase reactivateCheque,
                            MarkChequeAsCollectedUseCase markAsCollected,
                            MarkChequeAsPaidUseCase markAsPaid,
                            MarkOverdueChequesIfNecessaryUseCase markOverdueBatch,
                            ChequeQueryService chequeQueries) {
        this.addCheque = addCheque;
        this.updateCheque = updateCheque;
        this.softDeleteCheque = softDeleteCheque;
        this.hardDeleteCheque = hardDeleteCheque;
        this.cancelCheque = cancelCheque;
        this.reactivateCheque = reactivateCheque;
        this.markAsCollected = markAsCollected;
        this.markAsPaid = markAsPaid;
        this.markOverdueBatch = markOverdueBatch;
        this.chequeQueries = chequeQueries;
    }


    @PostMapping
    public ResponseEntity<ChequeDto> create(@Valid @RequestBody
                                                CreateChequeDtoRequest createChequeDtoRequest) {
        ChequeDto created = addCheque.add(createChequeDtoRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

        @GetMapping("/{id}")
    public ResponseEntity<ChequeDto> getByIdAndActive(@PathVariable String id,
                                                      @RequestParam(defaultValue = "true")
                                                      boolean active) {
        return ResponseEntity.ok(chequeQueries.findChequeByIdAndActive(id, active));
    }

    @GetMapping("/by-number/{number}")
    public ResponseEntity<ChequeDto> getByNumberAndActive(@PathVariable("number") String chequeNumber,
                                                          @RequestParam(defaultValue = "true")
                                                          boolean active) {
        return ResponseEntity.ok(chequeQueries.findChequeByNumberAndActive(chequeNumber, active));
    }

    @GetMapping
    public ResponseEntity<Set<ChequeDto>> listByActive(@RequestParam(defaultValue = "true")
                                                           boolean active) {
        return ResponseEntity.ok(chequeQueries.listChequesByActive(active));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Set<ChequeDto>> listByStatusAndActive(@PathVariable ChequeStatus status,
                                                                @RequestParam(defaultValue = "true")
                                                                boolean active) {
        return ResponseEntity.ok(chequeQueries.listChequesByStatusAndActive(status, active));
    }

    @GetMapping("/by-account/{bankAccountId}")
    public ResponseEntity<Set<ChequeDto>> listByBankAccountAndActive(@PathVariable String bankAccountId,
                                                                     @RequestParam(defaultValue = "true") boolean active) {
        return ResponseEntity.ok(chequeQueries.listChequesByBankAccountAndActive(bankAccountId, active));
    }

    @GetMapping("/created-between")
    public ResponseEntity<Set<ChequeDto>> listByCreatedBetweenAndActive(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @RequestParam(defaultValue = "true") boolean active) {
        return ResponseEntity.ok(chequeQueries.listChequesByCreatedAtBetweenAndActive(start, end, active));
    }

    @GetMapping("/due-before")
    public ResponseEntity<Set<ChequeDto>> listByDueBeforeStatusActive(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime now,
            @RequestParam ChequeStatus status,
            @RequestParam(defaultValue = "true") boolean active) {
        return ResponseEntity.ok(chequeQueries.listChequesByDueDateBeforeStatusActive(now, status, active));
    }

    @GetMapping("/due-between")
    public ResponseEntity<Set<ChequeDto>> listByDueBetweenStatusActive(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @RequestParam ChequeStatus status,
            @RequestParam(defaultValue = "true") boolean active) {
        return ResponseEntity.ok(chequeQueries.listChequesByDueDateBetweenStatusActive
                (start, end, status, active));
    }

    @GetMapping("/status/{status}/by-account/{bankAccountId}")
    public ResponseEntity<Set<ChequeDto>> listByStatusBankAccountAndActive(
            @PathVariable ChequeStatus status,
            @PathVariable String bankAccountId,
            @RequestParam(defaultValue = "true") boolean active) {
        return ResponseEntity.ok(chequeQueries.listChequesByStatusBankAccountAndActive
                (status, bankAccountId, active));
    }

        @PutMapping
    public ResponseEntity<ChequeDto> update(@Valid @RequestBody
                                                    UpdateChequeDtoRequest updateChequeDtoRequest) {
        return ResponseEntity.ok(updateCheque.update(updateChequeDtoRequest));
    }

        @PostMapping("/{id}/soft-delete")
    public ResponseEntity<ChequeDto> softDelete(@PathVariable String id) {
        return ResponseEntity.ok(softDeleteCheque.softDelete(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> hardDelete(@PathVariable String id) {
        hardDeleteCheque.hardDelete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<ChequeDto> cancel(@PathVariable String id) {
        return ResponseEntity.ok(cancelCheque.cancel(id));
    }

    @PostMapping("/{id}/reactivate")
    public ResponseEntity<ChequeDto> reactivate(@PathVariable String id) {
        return ResponseEntity.ok(reactivateCheque.reactivate(id));
    }

    @PostMapping("/{id}/collect")
    public ResponseEntity<ChequeDto> markCollected(@PathVariable String id) {
        return ResponseEntity.ok(markAsCollected.markAsCollected(id));
    }

    @PostMapping("/{id}/pay")
    public ResponseEntity<ChequeDto> markPaid(@PathVariable String id) {
        return ResponseEntity.ok(markAsPaid.markAsPaid(id));
    }

        @PostMapping("/overdue/mark")
    public ResponseEntity<Set<ChequeDto>> markOverdueBatch() {
        return ResponseEntity.ok(markOverdueBatch.markOverdueChequesIfNecessary());
    }
}
