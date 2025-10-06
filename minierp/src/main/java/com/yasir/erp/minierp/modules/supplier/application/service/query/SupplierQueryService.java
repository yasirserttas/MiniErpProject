package com.yasir.erp.minierp.modules.supplier.application.service.query;

import com.yasir.erp.minierp.modules.supplier.application.converter.SupplierConverter;
import com.yasir.erp.minierp.modules.supplier.application.dto.SupplierDto;
import com.yasir.erp.minierp.modules.supplier.domain.model.Supplier;
import com.yasir.erp.minierp.modules.supplier.domain.port.inbound.query.FindSupplierByIdAndActiveUseCase;
import com.yasir.erp.minierp.modules.supplier.domain.port.inbound.query.FindSupplierByTaxNumberAndActiveUseCase;
import com.yasir.erp.minierp.modules.supplier.domain.port.inbound.query.ListSuppliersByActiveUseCase;
import com.yasir.erp.minierp.modules.supplier.domain.port.inbound.query.ListSuppliersByNameContainingIgnoreCaseAndActiveUseCase;
import com.yasir.erp.minierp.modules.supplier.domain.port.inbound.query.ListSuppliersByUpdateAtBetweenAndActiveUseCase;
import com.yasir.erp.minierp.modules.supplier.domain.port.outbound.query.SupplierActiveQueryPort;
import com.yasir.erp.minierp.modules.supplier.domain.port.outbound.query.SupplierByIdAndActiveQueryPort;
import com.yasir.erp.minierp.modules.supplier.domain.port.outbound.query.SupplierByNameContainingIgnoreCaseAndActiveQueryPort;
import com.yasir.erp.minierp.modules.supplier.domain.port.outbound.query.SupplierByTaxNumberAndActiveQueryPort;
import com.yasir.erp.minierp.modules.supplier.domain.port.outbound.query.SupplierByUpdateAtBetweenAndActiveQueryPort;
import com.yasir.erp.minierp.modules.supplier.application.exception.SupplierNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;

@Service
public class SupplierQueryService implements
        FindSupplierByIdAndActiveUseCase,
        FindSupplierByTaxNumberAndActiveUseCase,
        ListSuppliersByActiveUseCase,
        ListSuppliersByNameContainingIgnoreCaseAndActiveUseCase,
        ListSuppliersByUpdateAtBetweenAndActiveUseCase {

    private final SupplierConverter converter;

    private final SupplierByIdAndActiveQueryPort byIdAndActivePort;
    private final SupplierByTaxNumberAndActiveQueryPort byTaxAndActivePort;
    private final SupplierActiveQueryPort activePort;
    private final SupplierByNameContainingIgnoreCaseAndActiveQueryPort nameActivePort;
    private final SupplierByUpdateAtBetweenAndActiveQueryPort updatedAtActivePort;

    public SupplierQueryService(SupplierConverter converter,
                                SupplierByIdAndActiveQueryPort byIdAndActivePort,
                                SupplierByTaxNumberAndActiveQueryPort byTaxAndActivePort,
                                SupplierActiveQueryPort activePort,
                                SupplierByNameContainingIgnoreCaseAndActiveQueryPort nameActivePort,
                                SupplierByUpdateAtBetweenAndActiveQueryPort updatedAtActivePort) {
        this.converter = converter;
        this.byIdAndActivePort = byIdAndActivePort;
        this.byTaxAndActivePort = byTaxAndActivePort;
        this.activePort = activePort;
        this.nameActivePort = nameActivePort;
        this.updatedAtActivePort = updatedAtActivePort;
    }


    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public SupplierDto findDtoByIdAndActive(String supplierId, Boolean active) {
        return converter.convertToSupplierDto(findByIdAndActive(supplierId, active));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public SupplierDto findDtoByTaxNumberAndActive(String taxNumber, Boolean active) {
        return converter.convertToSupplierDto(findByTaxNumberAndActive(taxNumber, active));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<SupplierDto> findDtoAllByActive(Boolean active) {
        return converter.convertToSupplierSetDto(findAllByActive(active));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<SupplierDto> findDtoAllByNameContainingIgnoreCaseAndActive(String name, Boolean active) {
        return converter.convertToSupplierSetDto(findAllByNameContainingIgnoreCaseAndActive(name, active));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<SupplierDto> findDtoAllByUpdateAtBetweenAndActive(LocalDateTime start, LocalDateTime end, Boolean active) {
        return converter.convertToSupplierSetDto(findAllByUpdateAtBetweenAndActive(start, end, active));
    }


    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Supplier findByIdAndActive(String supplierId, Boolean active) {
        return byIdAndActivePort.findByIdAndActive(supplierId, active)
                .orElseThrow(() -> new SupplierNotFoundException(supplierId));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Supplier findByTaxNumberAndActive(String taxNumber, Boolean active) {
        return byTaxAndActivePort.findByTaxNumberAndActive(taxNumber, active)
                .orElseThrow(() -> new SupplierNotFoundException(taxNumber));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Set<Supplier> findAllByActive(Boolean active) {
        return activePort.findAllByActive(active);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Set<Supplier> findAllByNameContainingIgnoreCaseAndActive(String name, Boolean active) {
        return nameActivePort.findAllByNameContainingIgnoreCaseAndActive(name, active);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Set<Supplier> findAllByUpdateAtBetweenAndActive(LocalDateTime start, LocalDateTime end, Boolean active) {
        return updatedAtActivePort.findAllByUpdateAtBetweenAndActive(start, end, active);
    }
}
