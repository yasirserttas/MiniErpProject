package com.yasir.erp.minierp.modules.receipt.application.converter;

import com.yasir.erp.minierp.modules.payment.application.converter.PaymentConverter;
import com.yasir.erp.minierp.modules.receipt.application.dto.ReceiptDto;
import com.yasir.erp.minierp.modules.receipt.domain.model.Receipt;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ReceiptConverter {

    private final PaymentConverter paymentConverter;

    public ReceiptConverter(PaymentConverter paymentConverter) {
        this.paymentConverter = paymentConverter;
    }

    public ReceiptDto convertToReceiptDto(Receipt receipt){

        return new ReceiptDto(
                receipt.getId(),
                paymentConverter.convertToPaymentDto(receipt.getPayment()),
                receipt.getReceiptNumber(),
                receipt.getIssuedBy(),
                receipt.getReceivedBy(),
                receipt.getReceiptDate(),
                receipt.getCreatedAt(),
                receipt.getUpdatedAt(),
                receipt.getActive()
        );
    }

    public Set<ReceiptDto> convertToReceiptSetDto(Set<Receipt> receipts){
        return receipts.stream().map(this::convertToReceiptDto).collect(Collectors.toSet());
    }
}
