package com.yasir.erp.minierp.modules.receipt.application.service.command;

import com.yasir.erp.minierp.modules.receipt.application.exception.ReceiptNotFoundException;
import com.yasir.erp.minierp.modules.receipt.application.converter.ReceiptConverter;
import com.yasir.erp.minierp.modules.receipt.application.dto.ReceiptDto;
import com.yasir.erp.minierp.modules.receipt.domain.model.Receipt;
import com.yasir.erp.minierp.modules.receipt.domain.port.inbound.command.ActivateReceiptUseCase;
import com.yasir.erp.minierp.modules.receipt.domain.port.outbound.command.ReceiptCommandPort;
import com.yasir.erp.minierp.modules.receipt.domain.port.outbound.query.ReceiptQueryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ActivateReceiptService implements ActivateReceiptUseCase {

    private final ReceiptCommandPort commandPort;
    private final ReceiptQueryPort queryPort;
    private final ReceiptPdfService receiptPdfService;
    private final ReceiptConverter converter;


    public ActivateReceiptService(ReceiptCommandPort commandPort,
                                  ReceiptQueryPort queryPort,
                                  ReceiptPdfService receiptPdfService,
                                  ReceiptConverter converter) {
        this.commandPort = commandPort;
        this.queryPort = queryPort;
        this.receiptPdfService = receiptPdfService;
        this.converter = converter;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ReceiptDto activateReceipt(String id) {
        Receipt existing = queryPort.findByIdAndActive(id, false)
                .orElseThrow(() -> new ReceiptNotFoundException(id));

        Receipt activated = new Receipt(
                existing.getId(), existing.getPayment(), existing.getReceiptNumber(),
                existing.getIssuedBy(), existing.getReceivedBy(), existing.getReceiptDate(),
                existing.getCreatedAt(), LocalDateTime.now(), true
        );
        Receipt saved = commandPort.save(activated);
        ReceiptDto dto = converter.convertToReceiptDto(saved);
        receiptPdfService.generateReceiptPdf(dto);
        return dto;
    }
}
