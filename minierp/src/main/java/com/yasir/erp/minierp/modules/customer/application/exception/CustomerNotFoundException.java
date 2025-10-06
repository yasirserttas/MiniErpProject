package com.yasir.erp.minierp.modules.customer.application.exception;

import com.yasir.erp.minierp.common.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CustomerNotFoundException extends BusinessException {

    public CustomerNotFoundException(UUID customerId) {
        super("CUSTOMER_NOT_FOUND",String.format("Customer not found. ID[%s]",customerId.toString()));
    }
}
