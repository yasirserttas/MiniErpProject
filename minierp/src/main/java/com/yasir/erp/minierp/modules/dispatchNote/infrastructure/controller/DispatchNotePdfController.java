package com.yasir.erp.minierp.modules.dispatchNote.infrastructure.controller;

import com.yasir.erp.minierp.dto.dispatchNote.DispatchNoteDto;
import com.yasir.erp.minierp.modules.dispatchNote.application.service.command.DispatchNotePdfService;
import com.yasir.erp.minierp.modules.dispatchNote.domain.port.inbound.query.FindDispatchNoteByIdUseCase;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dispatch-notes")
public class DispatchNotePdfController {

    private final FindDispatchNoteByIdUseCase findDispatchNoteByIdUseCase;
    private final DispatchNotePdfService dispatchNotePdfService;

    public DispatchNotePdfController(FindDispatchNoteByIdUseCase findDispatchNoteByIdUseCase,
                                     DispatchNotePdfService dispatchNotePdfService) {
        this.findDispatchNoteByIdUseCase = findDispatchNoteByIdUseCase;
        this.dispatchNotePdfService = dispatchNotePdfService;
    }

    @GetMapping("/{dispatchNoteId}/pdf")
    public ResponseEntity<byte[]> getDispatchNotePdf(
            @PathVariable String dispatchNoteId,
            @RequestParam(defaultValue = "true") boolean active
    ) {
        DispatchNoteDto dto = findDispatchNoteByIdUseCase.findDtoByIdAndActive(dispatchNoteId, active);

        byte[] pdfBytes = dispatchNotePdfService.generateDispatchNotePdf(dto);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=dispatchnote-" + dispatchNoteId + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}
