package com.yasir.erp.minierp.modules.invoice.infrastructure.controller;

import com.yasir.erp.minierp.modules.invoice.application.dto.InvoiceDto;
import com.yasir.erp.minierp.modules.invoice.application.service.command.InvoicePdfService;
import com.yasir.erp.minierp.modules.invoice.domain.port.inbound.query.FindInvoiceByIdUseCase;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invoices") // veya /api/v1/invoices: tek yere sabitle
public class InvoicePdfController {

    private final InvoicePdfService invoicePdfService;
    private final FindInvoiceByIdUseCase findInvoiceByIdUseCase;

    public InvoicePdfController(InvoicePdfService invoicePdfService,
                                FindInvoiceByIdUseCase findInvoiceByIdUseCase) {
        this.invoicePdfService = invoicePdfService;
        this.findInvoiceByIdUseCase = findInvoiceByIdUseCase;
    }

    @GetMapping(value = "/{invoiceId}/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> getInvoicePdf(
            @PathVariable String invoiceId,
            @RequestParam(defaultValue = "true") boolean active) {

        InvoiceDto invoice = findInvoiceByIdUseCase.findDtoByIdAndActive(invoiceId, active);

        byte[] pdfBytes = invoicePdfService.generateInvoicePdf(invoice);

        String safeFileName = ("invoice-" + invoiceId + ".pdf").replaceAll("[\\r\\n\"\\\\]", "_");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + safeFileName + "\"")
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(pdfBytes.length)
                // .cacheControl(CacheControl.noCache()) // istersen aç
                .body(pdfBytes);
    }
}



