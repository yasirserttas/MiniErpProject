package com.yasir.erp.minierp.modules.supplier.application.exception;

import com.yasir.erp.minierp.common.exception.BusinessException;

public class SupplierNotFoundException extends BusinessException {

    public SupplierNotFoundException(String supplierId) {
        super("SUPPLIER_NOT_FOUND" ,String.format("Supplier not found. ID[%s]",supplierId));
    }
}
