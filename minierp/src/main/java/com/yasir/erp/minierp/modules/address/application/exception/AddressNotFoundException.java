package com.yasir.erp.minierp.modules.address.application.exception;

import com.yasir.erp.minierp.common.exception.BusinessException;

public class AddressNotFoundException extends BusinessException {

    public AddressNotFoundException(String addressId) {
        super(
                "ADDRESS_NOT_FOUND",
                String.format("Address not found. ID: [%s]", addressId)
        );
    }
}
