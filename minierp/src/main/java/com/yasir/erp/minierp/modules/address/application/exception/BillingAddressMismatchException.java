package com.yasir.erp.minierp.modules.address.application.exception;


import com.yasir.erp.minierp.common.exception.BusinessException;

import java.util.UUID;

public class BillingAddressMismatchException extends BusinessException {
    public BillingAddressMismatchException(String addressId, UUID customerId) {
        super("BILLING_ADDRESS_MISMATCH",
                String.format("The provided billing address (ID: %s) does not belong to the customer (ID: %s)."
                        , addressId, customerId.toString()));
    }
}
