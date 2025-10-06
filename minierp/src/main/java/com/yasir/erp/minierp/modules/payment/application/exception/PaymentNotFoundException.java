package com.yasir.erp.minierp.modules.payment.application.exception;

import com.yasir.erp.minierp.common.exception.BusinessException;

public class PaymentNotFoundException extends BusinessException {

    public PaymentNotFoundException(String paymentId){
        super("PAYMENT_NOT_FOUND",String.format("Payment not found. ID[%s]",paymentId));
    }



}
