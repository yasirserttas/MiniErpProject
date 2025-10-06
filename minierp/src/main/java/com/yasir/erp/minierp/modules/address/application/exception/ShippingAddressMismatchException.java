package com.yasir.erp.minierp.modules.address.application.exception;

import com.yasir.erp.minierp.common.exception.BusinessException;

import java.util.UUID;

public class ShippingAddressMismatchException extends BusinessException {

    public ShippingAddressMismatchException(String addressId, UUID customerId) {
        super("SHIPPING_ADDRESS_MISMATCH",
                String.format
                        ("The provided Shipping address (ID: %s) does not belong to the customer (ID: %s).",
                                addressId, customerId.toString()));
    }
}
