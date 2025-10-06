package com.yasir.erp.minierp.modules.product.infrastructure.persistence;

import com.yasir.erp.minierp.modules.product.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface ProductJpaRepository extends JpaRepository<Product, String> {
    Optional<Product> findByIdAndActive(String productId, Boolean active);
    Set<Product> findAllByUser_IdAndActive(UUID userId, Boolean active);
    Set<Product> findAllByActive(Boolean active);
}
