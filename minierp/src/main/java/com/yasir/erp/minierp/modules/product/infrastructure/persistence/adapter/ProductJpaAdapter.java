package com.yasir.erp.minierp.modules.product.infrastructure.persistence.adapter;

import com.yasir.erp.minierp.modules.product.domain.model.Product;
import com.yasir.erp.minierp.modules.product.domain.port.outbound.command.ProductCommandPort;
import com.yasir.erp.minierp.modules.product.domain.port.outbound.query.ProductQueryPort;
import com.yasir.erp.minierp.modules.product.infrastructure.persistence.ProductJpaRepository;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Component
public class ProductJpaAdapter implements ProductCommandPort, ProductQueryPort {

    private final ProductJpaRepository productJpaRepository;

    public ProductJpaAdapter(ProductJpaRepository productJpaRepository) {
        this.productJpaRepository = productJpaRepository;
    }

    @Override public Product save(Product product) {
        return productJpaRepository.save(product);
    }

    @Override public void delete(Product product) {
        productJpaRepository.delete(product);
    }

    @Override public Optional<Product> findByIdAndActive(String productId, Boolean active) {
        return productJpaRepository.findByIdAndActive(productId, active);
    }

    @Override public Set<Product> findAllByUserIdAndActive(UUID userId, Boolean active) {
        return productJpaRepository.findAllByUser_IdAndActive(userId, active);
    }

    @Override public Set<Product> findAllByActive(Boolean active) {
        return productJpaRepository.findAllByActive(active);
    }

    @Override public Set<Product> findAll() {
        return new HashSet<>(productJpaRepository.findAll());
    }
}
