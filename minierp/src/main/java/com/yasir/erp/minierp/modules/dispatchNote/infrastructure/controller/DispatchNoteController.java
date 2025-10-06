package com.yasir.erp.minierp.modules.dispatchNote.infrastructure.controller;

import com.yasir.erp.minierp.dto.dispatchNote.DispatchNoteDto;
import com.yasir.erp.minierp.dto.dispatchNote.request.CreateDispatchNoteDtoRequest;
import com.yasir.erp.minierp.dto.dispatchNote.request.UpdateDispatchNoteDtoRequest;
import com.yasir.erp.minierp.modules.dispatchNote.application.service.query.DispatchNoteQueryService;
import com.yasir.erp.minierp.modules.dispatchNote.domain.model.DispatchNoteStatus;
import com.yasir.erp.minierp.modules.dispatchNote.domain.port.inbound.command.*;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/dispatch-notes")
public class DispatchNoteController {

    private final CreateDispatchNoteUseCase createUseCase;
    private final UpdateDispatchNoteUseCase updateUseCase;
    private final ActivateDispatchNoteUseCase activateUseCase;
    private final DeactivateDispatchNoteUseCase deactivateUseCase;
    private final HardDeleteDispatchNoteUseCase hardDeleteUseCase;
    private final DispatchedDispatchNoteUseCase dispatchedUseCase;
    private final DeliveredDispatchNoteUseCase deliveredUseCase;
    private final CancelDispatchNoteUseCase cancelUseCase;

    private final DispatchNoteQueryService queries;

    public DispatchNoteController(CreateDispatchNoteUseCase createUseCase,
                                  UpdateDispatchNoteUseCase updateUseCase,
                                  ActivateDispatchNoteUseCase activateUseCase,
                                  DeactivateDispatchNoteUseCase deactivateUseCase,
                                  HardDeleteDispatchNoteUseCase hardDeleteUseCase,
                                  DispatchedDispatchNoteUseCase dispatchedUseCase,
                                  DeliveredDispatchNoteUseCase deliveredUseCase,
                                  CancelDispatchNoteUseCase cancelUseCase,
                                  DispatchNoteQueryService queries) {
        this.createUseCase = createUseCase;
        this.updateUseCase = updateUseCase;
        this.activateUseCase = activateUseCase;
        this.deactivateUseCase = deactivateUseCase;
        this.hardDeleteUseCase = hardDeleteUseCase;
        this.dispatchedUseCase = dispatchedUseCase;
        this.deliveredUseCase = deliveredUseCase;
        this.cancelUseCase = cancelUseCase;
        this.queries = queries;
    }

    @PostMapping
    public ResponseEntity<DispatchNoteDto> create(@Valid @RequestBody
                                                      CreateDispatchNoteDtoRequest
                                                              createDispatchNoteDtoRequest) {
        DispatchNoteDto created = createUseCase.createDispatchNote(createDispatchNoteDtoRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping
    public ResponseEntity<DispatchNoteDto> update(@Valid @RequestBody
                                                      UpdateDispatchNoteDtoRequest
                                                              updateDispatchNoteDtoRequest) {
        return ResponseEntity.ok(updateUseCase.updateDispatchNote(updateDispatchNoteDtoRequest));
    }

    @PostMapping("/{id}/activate")
    public ResponseEntity<DispatchNoteDto> activate(@PathVariable String id) {
        return ResponseEntity.ok(activateUseCase.activeDispatchNote(id));
    }

    @PostMapping("/{id}/deactivate")
    public ResponseEntity<DispatchNoteDto> deactivate(@PathVariable String id) {
        return ResponseEntity.ok(deactivateUseCase.deactivateDispatchNote(id));
    }

    @PostMapping("/{id}/dispatched")
    public ResponseEntity<DispatchNoteDto> markDispatched(@PathVariable String id) {
        return ResponseEntity.ok(dispatchedUseCase.dispatchedDispatchNote(id));
    }

    @PostMapping("/{id}/delivered")
    public ResponseEntity<DispatchNoteDto> markDelivered(@PathVariable String id) {
        return ResponseEntity.ok(deliveredUseCase.deliveredDispatchNote(id));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<DispatchNoteDto> cancel(@PathVariable String id) {
        return ResponseEntity.ok(cancelUseCase.cancelDispatchNote(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> hardDelete(@PathVariable String id) {
        hardDeleteUseCase.hardDeleteById(id);
        return ResponseEntity.noContent().build();
    }

    // QUERIES
    @GetMapping("/{id}")
    public ResponseEntity<DispatchNoteDto> getById(@PathVariable String id,
                                                   @RequestParam(defaultValue = "true")
                                                   boolean active) {
        return ResponseEntity.ok(queries.findDtoByIdAndActive(id, active));
    }

    @GetMapping("/by-number/{number}")
    public ResponseEntity<DispatchNoteDto> getByNumber(@PathVariable String number,
                                                       @RequestParam(defaultValue = "true")
                                                       boolean active) {
        return ResponseEntity.ok(queries.findDtoByDispatchNoteNumberAndActive(number, active));
    }

    @GetMapping("/by-order/{orderId}")
    public ResponseEntity<Set<DispatchNoteDto>> listByOrder(@PathVariable String orderId,
                                                            @RequestParam(defaultValue = "true")
                                                            boolean active) {
        return ResponseEntity.ok(queries.findDtoAllByOrderIdAndActive(orderId, active));
    }

    @GetMapping("/by-customer/{customerId}")
    public ResponseEntity<Set<DispatchNoteDto>> listByCustomer(@PathVariable UUID customerId,
                                                               @RequestParam(defaultValue = "true")
                                                               boolean active) {
        return ResponseEntity.ok(queries.findDtoAllByCustomerIdAndActive(customerId, active));
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<Set<DispatchNoteDto>> listByUser(@PathVariable UUID userId,
                                                           @RequestParam(defaultValue = "true")
                                                           boolean active) {
        return ResponseEntity.ok(queries.findDtoAllByUserIdAndActive(userId, active));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Set<DispatchNoteDto>> listByStatus(@PathVariable DispatchNoteStatus status,
                                                             @RequestParam(defaultValue = "true")
                                                             boolean active) {
        return ResponseEntity.ok(queries.findDtoAllByDispatchNoteStatusAndActive(status, active));
    }

    @GetMapping("/dispatch-date-between")
    public ResponseEntity<Set<DispatchNoteDto>> listByDispatchDateBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @RequestParam(defaultValue = "true") boolean active) {
        return ResponseEntity.ok(queries.findDtoAllByDispatchDateBetweenAndActive(start, end, active));
    }

    @GetMapping("/status/{status}/dispatch-date-between")
    public ResponseEntity<Set<DispatchNoteDto>> listByStatusAndDispatchDateBetween(
            @PathVariable DispatchNoteStatus status,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @RequestParam(defaultValue = "true") boolean active) {
        return ResponseEntity.ok(
                queries.findDtoAllByDispatchNoteStatusAndDispatchDateBetweenAndActive
                        (status, start, end, active)
        );
    }
}
