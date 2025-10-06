package com.yasir.erp.minierp.modules.bankAccount.infrastructure.controller;

import com.yasir.erp.minierp.modules.bankAccount.application.dto.BankAccountDto;
import com.yasir.erp.minierp.modules.bankAccount.application.dto.request.CreateBankAccountDtoRequest;
import com.yasir.erp.minierp.modules.bankAccount.application.dto.request.UpdateBankAccountDtoRequest;
import com.yasir.erp.minierp.modules.bankAccount.application.service.query.FindBankAccountByIdService;
import com.yasir.erp.minierp.modules.bankAccount.application.service.query.ListBankAccountsByStatusService;
import com.yasir.erp.minierp.modules.bankAccount.domain.model.BankAccountStatus;
import com.yasir.erp.minierp.modules.bankAccount.domain.port.inbound.command.*;
import com.yasir.erp.minierp.modules.bankMovement.domain.model.BankMovementType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Set;

@RestController
@RequestMapping("/api/bank-accounts")
@Validated
public class BankAccountController {

    private final AddBankAccountUseCase addBankAccount;
    private final UpdateBankAccountUseCase updateBankAccount;
    private final ActivateBankAccountUseCase activateBankAccount;
    private final DeactivateBankAccountUseCase deactivateBankAccount;
    private final UpdateBankAccountBalanceUseCase updateBalanceUseCase;
    private final FindBankAccountByIdService findByIdService;
    private final ListBankAccountsByStatusService listByStatusService;

    public BankAccountController(AddBankAccountUseCase addBankAccount,
                                 UpdateBankAccountUseCase updateBankAccount,
                                 ActivateBankAccountUseCase activateBankAccount,
                                 DeactivateBankAccountUseCase deactivateBankAccount,
                                 UpdateBankAccountBalanceUseCase updateBalanceUseCase,
                                 FindBankAccountByIdService findByIdService,
                                 ListBankAccountsByStatusService listByStatusService) {
        this.addBankAccount = addBankAccount;
        this.updateBankAccount = updateBankAccount;
        this.activateBankAccount = activateBankAccount;
        this.deactivateBankAccount = deactivateBankAccount;
        this.updateBalanceUseCase = updateBalanceUseCase;
        this.findByIdService = findByIdService;
        this.listByStatusService = listByStatusService;
    }

    
    @PostMapping
    public ResponseEntity<BankAccountDto> create(@Valid @RequestBody CreateBankAccountDtoRequest
                                                         createBankAccountDtoRequest) {
        BankAccountDto created = addBankAccount.addBankAccount(createBankAccountDtoRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<BankAccountDto> getById(@PathVariable String id,
                                                  @RequestParam BankAccountStatus status) {
        return ResponseEntity.ok(findByIdService.find(id, status));
    }

    
    @GetMapping
    public ResponseEntity<Set<BankAccountDto>> listByStatus
    (@RequestParam(defaultValue = "ACTIVE") BankAccountStatus status) {
        return ResponseEntity.ok(listByStatusService.list(status));
    }

    
    @PutMapping
    public ResponseEntity<BankAccountDto> update(@Valid @RequestBody UpdateBankAccountDtoRequest
                                                         updateBankAccountDtoRequest) {
        return ResponseEntity.ok(updateBankAccount.update(updateBankAccountDtoRequest));
    }

    
    @PostMapping("/{id}/activate")
    public ResponseEntity<BankAccountDto> activate(@PathVariable String id) {
        return ResponseEntity.ok(activateBankAccount.activate(id));
    }

    
    @PostMapping("/{id}/deactivate")
    public ResponseEntity<BankAccountDto> deactivate(@PathVariable String id) {
        return ResponseEntity.ok(deactivateBankAccount.deactivate(id));
    }

    
    @PatchMapping("/{id}/balance")
    public ResponseEntity<BankAccountDto> updateBalance(@PathVariable String id,
                                                        @Valid @RequestBody BalanceUpdateRequest body) {
        BankAccountDto updated = updateBalanceUseCase.updateBalance(id, body.amount(), body.type());
        return ResponseEntity.ok(updated);
    }

    public record BalanceUpdateRequest(
            @NotNull @DecimalMin(value = "0.00", inclusive = false) BigDecimal amount,
            @NotNull BankMovementType type
    ) {}
}
