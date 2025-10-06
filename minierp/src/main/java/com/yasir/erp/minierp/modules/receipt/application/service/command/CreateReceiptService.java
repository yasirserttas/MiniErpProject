package com.yasir.erp.minierp.modules.receipt.application.service.command;

import com.yasir.erp.minierp.modules.bankAccount.application.exception.BankAccountNotFoundException;
import com.yasir.erp.minierp.modules.payment.application.exception.PaymentNotFoundException;
import com.yasir.erp.minierp.modules.bankAccount.domain.model.BankAccount;
import com.yasir.erp.minierp.modules.bankAccount.domain.model.BankAccountStatus;
import com.yasir.erp.minierp.modules.bankAccount.domain.port.outbound.query.BankAccountQueryPort;
import com.yasir.erp.minierp.modules.bankMovement.application.dto.request.CreateBankMovementDtoRequest;
import com.yasir.erp.minierp.modules.bankMovement.domain.model.BankMovementType;
import com.yasir.erp.minierp.modules.bankMovement.domain.port.inbound.command.AddBankMovementUseCase;
import com.yasir.erp.minierp.modules.payment.domain.model.Payment;
import com.yasir.erp.minierp.modules.payment.domain.port.outbound.query.PaymentQueryPort;
import com.yasir.erp.minierp.modules.receipt.application.converter.ReceiptConverter;
import com.yasir.erp.minierp.modules.receipt.application.dto.ReceiptDto;
import com.yasir.erp.minierp.modules.receipt.application.dto.request.CreateReceiptDtoRequest;
import com.yasir.erp.minierp.modules.receipt.domain.model.Receipt;
import com.yasir.erp.minierp.modules.receipt.domain.port.inbound.command.CreateReceiptUseCase;
import com.yasir.erp.minierp.modules.receipt.domain.port.outbound.command.ReceiptCommandPort;
import com.yasir.erp.minierp.common.generator.ShortIdGenerator;
import com.yasir.erp.minierp.common.generator.UlidGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CreateReceiptService implements CreateReceiptUseCase {

    private final ReceiptCommandPort commandPort;
    private final PaymentQueryPort paymentQueryPort;
    private final BankAccountQueryPort bankAccountQueryPort;
    private final AddBankMovementUseCase addBankMovement;
    private final ReceiptConverter converter;
    private final ReceiptPdfService receiptPdfService;

    public CreateReceiptService(ReceiptCommandPort commandPort,
                                PaymentQueryPort paymentQueryPort,
                                BankAccountQueryPort bankAccountQueryPort,
                                AddBankMovementUseCase addBankMovement,
                                ReceiptConverter converter,
                                ReceiptPdfService receiptPdfService) {
        this.commandPort = commandPort;
        this.paymentQueryPort = paymentQueryPort;
        this.bankAccountQueryPort = bankAccountQueryPort;
        this.addBankMovement = addBankMovement;
        this.converter = converter;
        this.receiptPdfService = receiptPdfService;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ReceiptDto createReceipt(CreateReceiptDtoRequest req) {
        Payment payment = paymentQueryPort.findById(req.getPaymentId()).
                orElseThrow(()-> new PaymentNotFoundException(req.getPaymentId()));

        BankAccount bankAccount = bankAccountQueryPort.
                findByIdAndStatus(req.getBankAccountId(), BankAccountStatus.ACTIVE)
                .orElseThrow(()-> new BankAccountNotFoundException(req.getBankAccountId()));

        LocalDateTime receiptDate = (req.getReceiptDate() != null) ?
                req.getReceiptDate() : LocalDateTime.now();

        Receipt receipt = new Receipt(
                UlidGenerator.generate(),
                payment,
                ShortIdGenerator.generate(10),
                req.getIssuedBy(),
                req.getReceivedBy(),
                receiptDate,
                LocalDateTime.now(),
                LocalDateTime.now(),
                true
        );

        String bmDesc = "RECEIPT=" + receipt.getReceiptNumber()
                + " | PAYMENT_ID=" + payment.getId()
                + " | METHOD=" + payment.getMethod()
                + (payment.getDueDate() != null ? " | DUE_DATE=" + payment.getDueDate() : "")
                + (payment.getIssueDate() != null ? " | ISSUE_DATE=" + payment.getIssueDate() : "")
                + (payment.getNote() != null ? " | NOTE=" + payment.getNote() : "");
        addBankMovement.addBankMovement(new CreateBankMovementDtoRequest(
                bankAccount.getId(), payment.getAmount(), BankMovementType.INCOME, bmDesc
        ));

        Receipt saved = commandPort.save(receipt);
        ReceiptDto dto = converter.convertToReceiptDto(saved);
        receiptPdfService.generateReceiptPdf(dto);
        return dto;
    }
}
