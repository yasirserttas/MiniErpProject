package com.yasir.erp.minierp.modules.supplier.infrastructure.persistence;

import com.yasir.erp.minierp.modules.supplier.domain.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Repository
public interface SupplierJpaRepository extends JpaRepository<Supplier, String> {
    Optional<Supplier> findByIdAndActive(String id, Boolean active);
    Optional<Supplier> findByTaxNumberAndActive(String taxNumber, Boolean active);
    Set<Supplier> findAllByActive(Boolean active);
    Set<Supplier> findAllByNameContainingIgnoreCaseAndActive(String name, Boolean active);
    Set<Supplier> findAllByCreatedAtBetweenAndActive(LocalDateTime start, LocalDateTime end, Boolean active);
    Set<Supplier> findAllByUpdateAtBetweenAndActive(LocalDateTime start, LocalDateTime end, Boolean active);
}