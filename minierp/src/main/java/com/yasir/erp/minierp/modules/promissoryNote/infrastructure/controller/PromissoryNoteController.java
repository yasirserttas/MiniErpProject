package com.yasir.erp.minierp.modules.promissoryNote.infrastructure.controller;

import com.yasir.erp.minierp.modules.promissoryNote.application.dto.PromissoryNoteDto;
import com.yasir.erp.minierp.modules.promissoryNote.application.dto.request.CreatePromissoryNoteDtoRequest;
import com.yasir.erp.minierp.modules.promissoryNote.application.dto.request.UpdatePromissoryNoteDtoRequest;
import com.yasir.erp.minierp.modules.promissoryNote.domain.model.PromissoryNoteStatus;
import com.yasir.erp.minierp.modules.promissoryNote.domain.port.inbound.command.*;
import com.yasir.erp.minierp.modules.promissoryNote.domain.port.inbound.query.*;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Set;

@RestController
@RequestMapping("/api/promissory-notes")
public class PromissoryNoteController {

    private final CreatePromissoryNoteUseCase createNote;
    private final UpdatePromissoryNoteUseCase updateNote;
    private final ActivatePromissoryNoteUseCase activateNote;
    private final DeactivatePromissoryNoteUseCase deactivateNote;
    private final CancelPromissoryNoteUseCase cancelNote;
    private final MarkPaidPromissoryNoteUseCase markPaidNote;

    private final FindPromissoryNoteByIdUseCase findById;
    private final FindPromissoryNoteByNoteNumberUseCase findByNumber;
    private final ListPromissoryNotesByActiveUseCase listByActive;
    private final ListPromissoryNotesByAmountBetweenUseCase listByAmountBetween;
    private final ListPromissoryNotesByBankAccountUseCase listByBankAccount;
    private final ListPromissoryNotesByCreatedAtBetweenUseCase listByCreatedBetween;
    private final ListPromissoryNotesByDebtorUseCase listByDebtor;
    private final ListPromissoryNotesByDueDateAndStatusUseCase listByDueDateAndStatus;
    private final ListPromissoryNotesByDueDateBetweenUseCase listByDueDateBetween;
    private final ListPromissoryNotesByStatusUseCase listByStatus;
    private final ListPromissoryNotesByUpdatedAtBetweenUseCase listByUpdatedBetween;

    public PromissoryNoteController(CreatePromissoryNoteUseCase createNote,
                                    UpdatePromissoryNoteUseCase updateNote,
                                    ActivatePromissoryNoteUseCase activateNote,
                                    DeactivatePromissoryNoteUseCase deactivateNote,
                                    CancelPromissoryNoteUseCase cancelNote,
                                    MarkPaidPromissoryNoteUseCase markPaidNote,
                                    FindPromissoryNoteByIdUseCase findById,
                                    FindPromissoryNoteByNoteNumberUseCase findByNumber,
                                    ListPromissoryNotesByActiveUseCase listByActive,
                                    ListPromissoryNotesByAmountBetweenUseCase listByAmountBetween,
                                    ListPromissoryNotesByBankAccountUseCase listByBankAccount,
                                    ListPromissoryNotesByCreatedAtBetweenUseCase listByCreatedBetween,
                                    ListPromissoryNotesByDebtorUseCase listByDebtor,
                                    ListPromissoryNotesByDueDateAndStatusUseCase listByDueDateAndStatus,
                                    ListPromissoryNotesByDueDateBetweenUseCase listByDueDateBetween,
                                    ListPromissoryNotesByStatusUseCase listByStatus,
                                    ListPromissoryNotesByUpdatedAtBetweenUseCase listByUpdatedBetween) {
        this.createNote = createNote;
        this.updateNote = updateNote;
        this.activateNote = activateNote;
        this.deactivateNote = deactivateNote;
        this.cancelNote = cancelNote;
        this.markPaidNote = markPaidNote;
        this.findById = findById;
        this.findByNumber = findByNumber;
        this.listByActive = listByActive;
        this.listByAmountBetween = listByAmountBetween;
        this.listByBankAccount = listByBankAccount;
        this.listByCreatedBetween = listByCreatedBetween;
        this.listByDebtor = listByDebtor;
        this.listByDueDateAndStatus = listByDueDateAndStatus;
        this.listByDueDateBetween = listByDueDateBetween;
        this.listByStatus = listByStatus;
        this.listByUpdatedBetween = listByUpdatedBetween;
    }

    @PostMapping
    public ResponseEntity<PromissoryNoteDto> create(@Valid @RequestBody CreatePromissoryNoteDtoRequest req) {
        PromissoryNoteDto created = createNote.addPromissoryNote(req);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PromissoryNoteDto> update(@PathVariable String id,
                                                    @Valid @RequestBody UpdatePromissoryNoteDtoRequest req) {
        UpdatePromissoryNoteDtoRequest fixed = new UpdatePromissoryNoteDtoRequest(
                id, req.getAmount(), req.getDebtor(), req.getDueDate(), req.getNoteNumber()
        );
        return ResponseEntity.ok(updateNote.updatePromissoryNote(fixed));
    }

    @PostMapping("/{id}/activate")
    public ResponseEntity<PromissoryNoteDto> activate(@PathVariable String id) {
        return ResponseEntity.ok(activateNote.activatePromissoryNote(id));
    }

    @PostMapping("/{id}/deactivate")
    public ResponseEntity<PromissoryNoteDto> deactivate(@PathVariable String id) {
        return ResponseEntity.ok(deactivateNote.deactivatePromissoryNote(id));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<PromissoryNoteDto> cancel(@PathVariable String id) {
        return ResponseEntity.ok(cancelNote.cancelPromissoryNote(id));
    }

    @PostMapping("/{id}/pay")
    public ResponseEntity<PromissoryNoteDto> markPaid(@PathVariable String id) {
        return ResponseEntity.ok(markPaidNote.markPromissoryNoteAsPaid(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PromissoryNoteDto> getById(@PathVariable String id,
                                                     @RequestParam(defaultValue = "true") boolean active) {
        return ResponseEntity.ok(findById.findDtoByIdAndActive(id, active));
    }

    @GetMapping("/by-number/{noteNumber}")
    public ResponseEntity<PromissoryNoteDto> getByNumber(@PathVariable String noteNumber,
                                                         @RequestParam(defaultValue = "true") boolean active) {
        return ResponseEntity.ok(findByNumber.findDtoByNoteNumberAndActive(noteNumber, active));
    }

    @GetMapping
    public ResponseEntity<Set<PromissoryNoteDto>> listByActive(@RequestParam(defaultValue = "true") boolean active) {
        return ResponseEntity.ok(listByActive.findDtoAllByActive(active));
    }

    @GetMapping("/by-amount")
    public ResponseEntity<Set<PromissoryNoteDto>> listByAmountBetween(@RequestParam BigDecimal min,
                                                                      @RequestParam BigDecimal max,
                                                                      @RequestParam(defaultValue = "true") boolean active) {
        return ResponseEntity.ok(listByAmountBetween.findDtoAllByAmountBetweenAndActive(min, max, active));
    }

    @GetMapping("/by-bank-account/{bankAccountId}")
    public ResponseEntity<Set<PromissoryNoteDto>> listByBankAccount(@PathVariable String bankAccountId,
                                                                    @RequestParam(defaultValue = "true") boolean active) {
        return ResponseEntity.ok(listByBankAccount.findDtoAllByBankAccountIdAndActive(bankAccountId, active));
    }

    @GetMapping("/by-created-between")
    public ResponseEntity<Set<PromissoryNoteDto>> listByCreatedAtBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @RequestParam(defaultValue = "true") boolean active) {
        return ResponseEntity.ok(listByCreatedBetween.findDtoAllByCreatedAtBetweenAndActive(start, end, active));
    }

    @GetMapping("/by-debtor")
    public ResponseEntity<Set<PromissoryNoteDto>> listByDebtor(@RequestParam String debtor,
                                                               @RequestParam(defaultValue = "true") boolean active) {
        return ResponseEntity.ok(listByDebtor.findDtoAllByDebtorAndActive(debtor, active));
    }

    @GetMapping("/by-due-date-and-status")
    public ResponseEntity<Set<PromissoryNoteDto>> listByDueDateAndStatus(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @RequestParam PromissoryNoteStatus status,
            @RequestParam(defaultValue = "true") boolean active) {
        return ResponseEntity.ok(
                listByDueDateAndStatus.findDtoAllByDueDateBetweenAndStatusAndActive(start, end, status, active)
        );
    }

    @GetMapping("/by-due-date")
    public ResponseEntity<Set<PromissoryNoteDto>> listByDueDateBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @RequestParam(defaultValue = "true") boolean active) {
        return ResponseEntity.ok(listByDueDateBetween.findDtoAllByDueDateBetweenAndActive(start, end, active));
    }

    @GetMapping("/by-status")
    public ResponseEntity<Set<PromissoryNoteDto>> listByStatus(@RequestParam PromissoryNoteStatus status,
                                                               @RequestParam(defaultValue = "true") boolean active) {
        return ResponseEntity.ok(listByStatus.findDtoAllByStatusAndActive(status, active));
    }

    @GetMapping("/by-updated-between")
    public ResponseEntity<Set<PromissoryNoteDto>> listByUpdatedAtBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @RequestParam(defaultValue = "true") boolean active) {
        return ResponseEntity.ok(listByUpdatedBetween.findDtoAllByUpdatedAtBetweenAndActive(start, end, active));
    }
}
