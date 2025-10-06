package com.yasir.erp.minierp.modules.product.application.exception;

import com.yasir.erp.minierp.common.exception.BusinessException;

public class ProductNotFoundException extends BusinessException {

    public ProductNotFoundException(String productId) {
        super("PRODUCT_NOT_FOUND" ,String.format("Product not found. ID[%s]",productId));
    }
}
