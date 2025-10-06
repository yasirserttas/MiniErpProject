package com.yasir.erp.minierp.modules.bankMovement.infrastructure.controller;

import com.yasir.erp.minierp.modules.bankMovement.application.dto.BankMovementDto;
import com.yasir.erp.minierp.modules.bankMovement.application.dto.request.CreateBankMovementDtoRequest;
import com.yasir.erp.minierp.modules.bankMovement.application.service.query.BankMovementQueryService;
import com.yasir.erp.minierp.modules.bankMovement.domain.model.BankMovementStatus;
import com.yasir.erp.minierp.modules.bankMovement.domain.port.inbound.command.AddBankMovementUseCase;
import com.yasir.erp.minierp.modules.bankMovement.domain.port.inbound.command.CancelBankMovementUseCase;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Set;

@RestController
@RequestMapping("/api/bank-movements")
public class BankMovementController {

    private final AddBankMovementUseCase addBankMovement;
    private final CancelBankMovementUseCase cancelBankMovement;
    private final BankMovementQueryService bankMovementQueries;

    public BankMovementController(AddBankMovementUseCase addBankMovement,
                                  CancelBankMovementUseCase cancelBankMovement,
                                  BankMovementQueryService bankMovementQueries) {
        this.addBankMovement = addBankMovement;
        this.cancelBankMovement = cancelBankMovement;
        this.bankMovementQueries = bankMovementQueries;
    }

    
    @PostMapping
    public ResponseEntity<BankMovementDto> create(@Valid @RequestBody
                                                      CreateBankMovementDtoRequest
                                                              createBankMovementDtoRequest) {
        return ResponseEntity.ok(addBankMovement.addBankMovement(createBankMovementDtoRequest));
    }

    
    @PostMapping("/{id}/cancel")
    public ResponseEntity<Void> cancel(@PathVariable String id) {
        cancelBankMovement.cancel(id);
        return ResponseEntity.noContent().build();
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<BankMovementDto> getById(@PathVariable String id) {
        return ResponseEntity.ok(bankMovementQueries.findBankMovementById(id));
    }

    
    @GetMapping("/active")
    public ResponseEntity<Set<BankMovementDto>> listActive() {
        return ResponseEntity.ok(bankMovementQueries.listActiveBankMovement());
    }

    
    @GetMapping("/cancelled")
    public ResponseEntity<Set<BankMovementDto>> listCancelled() {
        return ResponseEntity.ok(bankMovementQueries.listCancelledBankMovement());
    }

    
    @GetMapping("/by-account/{bankAccountId}")
    public ResponseEntity<Set<BankMovementDto>> listByAccount(@PathVariable String bankAccountId) {
        return ResponseEntity.ok(bankMovementQueries.listBankMovementsByBankAccount(bankAccountId));
    }

    
    @GetMapping("/by-account/{bankAccountId}/status")
    public ResponseEntity<Set<BankMovementDto>> listByAccountAndStatus(@PathVariable String bankAccountId,
                                                                       @RequestParam BankMovementStatus status)
    {
        return ResponseEntity.ok(
                bankMovementQueries.listBankMovementsByBankAccountAndStatus(bankAccountId, status)
        );
    }

    
    @GetMapping("/by-account/{bankAccountId}/between")
    public ResponseEntity<Set<BankMovementDto>> listByAccountBetween(
            @PathVariable String bankAccountId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(
                bankMovementQueries.listBankMovementsByBankAccountAndDateBetween
                        (bankAccountId, start, end)
        );
    }

    
    @GetMapping("/by-account/{bankAccountId}/between/status")
    public ResponseEntity<Set<BankMovementDto>> listByAccountBetweenAndStatus(
            @PathVariable String bankAccountId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @RequestParam BankMovementStatus status) {
        return ResponseEntity.ok(
                bankMovementQueries.listBankMovementsByBankAccountAndDateBetweenAndStatus
                        (bankAccountId, start, end, status)
        );
    }

    
    @GetMapping("/between")
    public ResponseEntity<Set<BankMovementDto>> listBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(bankMovementQueries.listBankMovementsByDateBetween(start, end));
    }

    
    @GetMapping("/between/status")
    public ResponseEntity<Set<BankMovementDto>> listBetweenAndStatus(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @RequestParam BankMovementStatus status) {
        return ResponseEntity.ok(
                bankMovementQueries.listBankMovementsByDateBetweenAndStatus(start, end, status)
        );
    }

    
    @GetMapping("/status/{status}")
    public ResponseEntity<Set<BankMovementDto>> listByStatus(@PathVariable BankMovementStatus status) {
        return ResponseEntity.ok(bankMovementQueries.listBankMovementsByStatus(status));
    }
}