package com.yasir.erp.minierp.modules.product.domain.port.outbound.command;

import com.yasir.erp.minierp.modules.product.domain.model.Product;

public interface ProductCommandPort {
    Product save(Product product);
    void delete(Product product);
}