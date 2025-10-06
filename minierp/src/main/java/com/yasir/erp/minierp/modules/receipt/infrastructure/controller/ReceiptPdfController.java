package com.yasir.erp.minierp.modules.receipt.infrastructure.controller;

import com.yasir.erp.minierp.modules.receipt.application.dto.ReceiptDto;
import com.yasir.erp.minierp.modules.receipt.application.service.command.ReceiptPdfService;
import com.yasir.erp.minierp.modules.receipt.domain.port.inbound.query.FindReceiptByIdUseCase;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/receipts")
public class ReceiptPdfController {

    private final FindReceiptByIdUseCase findReceiptByIdUseCase;
    private final ReceiptPdfService receiptPdfService;

    public ReceiptPdfController(FindReceiptByIdUseCase findReceiptByIdUseCase,
                                ReceiptPdfService receiptPdfService) {
        this.findReceiptByIdUseCase = findReceiptByIdUseCase;
        this.receiptPdfService = receiptPdfService;
    }

    @GetMapping("/{receiptId}/pdf")
    public ResponseEntity<byte[]> getReceiptPdf(
            @PathVariable String receiptId,
            @RequestParam(defaultValue = "true") boolean active
    ) {
        ReceiptDto dto = findReceiptByIdUseCase.findById(receiptId, active);

        byte[] pdfBytes = receiptPdfService.generateReceiptPdf(dto);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=receipt-" + receiptId + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}
