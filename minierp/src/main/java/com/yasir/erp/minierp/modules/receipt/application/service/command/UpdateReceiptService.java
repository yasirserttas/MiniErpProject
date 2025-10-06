package com.yasir.erp.minierp.modules.receipt.application.service.command;

import com.yasir.erp.minierp.modules.receipt.application.exception.ReceiptNotFoundException;
import com.yasir.erp.minierp.modules.receipt.application.converter.ReceiptConverter;
import com.yasir.erp.minierp.modules.receipt.application.dto.ReceiptDto;
import com.yasir.erp.minierp.modules.receipt.application.dto.request.UpdateReceiptDtoRequest;
import com.yasir.erp.minierp.modules.receipt.domain.model.Receipt;
import com.yasir.erp.minierp.modules.receipt.domain.port.inbound.command.UpdateReceiptUseCase;
import com.yasir.erp.minierp.modules.receipt.domain.port.outbound.command.ReceiptCommandPort;
import com.yasir.erp.minierp.modules.receipt.domain.port.outbound.query.ReceiptQueryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UpdateReceiptService implements UpdateReceiptUseCase {

    private final ReceiptCommandPort commandPort;
    private final ReceiptQueryPort queryPort;
    private final ReceiptConverter converter;
    private final ReceiptPdfService receiptPdfService;

    public UpdateReceiptService(ReceiptCommandPort commandPort,
                                ReceiptQueryPort queryPort,
                                ReceiptConverter converter,
                                ReceiptPdfService receiptPdfService) {
        this.commandPort = commandPort;
        this.queryPort = queryPort;
        this.converter = converter;
        this.receiptPdfService = receiptPdfService;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ReceiptDto updateReceipt(UpdateReceiptDtoRequest updateReceiptDtoRequest) {

        Receipt existing = queryPort.findByIdAndActive(updateReceiptDtoRequest.getId(), true)
                .orElseThrow(() -> new ReceiptNotFoundException(updateReceiptDtoRequest.getId()));

        Receipt updated = new Receipt(
                existing.getId(),
                existing.getPayment(),
                existing.getReceiptNumber(),
                updateReceiptDtoRequest.getIssuedBy(),
                updateReceiptDtoRequest.getReceivedBy(),
                updateReceiptDtoRequest.getReceiptDate(),
                existing.getCreatedAt(),
                LocalDateTime.now(),
                true
        );

        Receipt saved = commandPort.save(updated);
        ReceiptDto dto = converter.convertToReceiptDto(saved);
        receiptPdfService.generateReceiptPdf(dto);
        return dto;
    }
}
