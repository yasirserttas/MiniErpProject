package com.yasir.erp.minierp.modules.supplier.infrastructure.persistence.adapter;

import com.yasir.erp.minierp.modules.supplier.domain.model.Supplier;
import com.yasir.erp.minierp.modules.supplier.domain.port.outbound.command.SupplierCommandPort;
import com.yasir.erp.minierp.modules.supplier.domain.port.outbound.query.*;
import com.yasir.erp.minierp.modules.supplier.infrastructure.persistence.SupplierJpaRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Component
public class SupplierJpaAdapter implements
        SupplierCommandPort,
        SupplierByIdAndActiveQueryPort,
        SupplierByTaxNumberAndActiveQueryPort,
        SupplierActiveQueryPort,
        SupplierByNameContainingIgnoreCaseAndActiveQueryPort,
        SupplierByUpdateAtBetweenAndActiveQueryPort {

    private final SupplierJpaRepository supplierJpaRepository;

    public SupplierJpaAdapter(SupplierJpaRepository supplierJpaRepository) {
        this.supplierJpaRepository = supplierJpaRepository;
    }

    @Override
    public Supplier save(Supplier supplier) {
        return supplierJpaRepository.save(supplier);
    }

    @Override
    public void delete(Supplier supplier) {
        supplierJpaRepository.delete(supplier);
    }

    @Override
    public Optional<Supplier> findByIdAndActive(String id, Boolean active) {
        return supplierJpaRepository.findByIdAndActive(id, active);
    }

    @Override
    public Optional<Supplier> findByTaxNumberAndActive(String taxNumber, Boolean active) {
        return supplierJpaRepository.findByTaxNumberAndActive(taxNumber, active);
    }

    @Override
    public Set<Supplier> findAllByActive(Boolean active) {
        return supplierJpaRepository.findAllByActive(active);
    }

    @Override
    public Set<Supplier> findAllByNameContainingIgnoreCaseAndActive(String name, Boolean active) {
        return supplierJpaRepository.findAllByNameContainingIgnoreCaseAndActive(name, active);
    }

    @Override
    public Set<Supplier> findAllByUpdateAtBetweenAndActive
            (LocalDateTime start, LocalDateTime end, Boolean active) {
        return supplierJpaRepository.findAllByUpdateAtBetweenAndActive(start, end, active);
    }
}