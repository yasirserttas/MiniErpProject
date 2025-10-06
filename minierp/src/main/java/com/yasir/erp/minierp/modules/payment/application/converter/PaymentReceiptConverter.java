package com.yasir.erp.minierp.modules.payment.application.converter;

import com.yasir.erp.minierp.modules.payment.application.dto.PaymentReceiptDto;
import com.yasir.erp.minierp.modules.payment.domain.model.Payment;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PaymentReceiptConverter {

    public PaymentReceiptDto convertToPaymentReceiptDto(Payment payment) {
        return new PaymentReceiptDto(
                payment.getId(),
                payment.getAmount(),
                payment.getMethod(),
                payment.getPaidAt(),
                payment.getCreatedAt(),
                payment.getUpdatedAt()
        );
    }

    public Set<PaymentReceiptDto> convertToSetPaymentReceiptDto(Set<Payment> payments) {
        return payments.stream()
                .map(this::convertToPaymentReceiptDto)
                .collect(Collectors.toSet());
    }
}
