package com.yasir.erp.minierp.modules.product.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.product.domain.model.Product;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface ProductQueryPort {
    Optional<Product> findByIdAndActive(String productId, Boolean active);
    Set<Product> findAllByUserIdAndActive(UUID userId, Boolean active);
    Set<Product> findAllByActive(Boolean active);
    Set<Product> findAll(); // wrap of JpaRepository#findAll
}
