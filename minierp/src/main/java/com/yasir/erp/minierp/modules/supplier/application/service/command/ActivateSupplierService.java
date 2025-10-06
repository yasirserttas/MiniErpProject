package com.yasir.erp.minierp.modules.supplier.application.service.command;

import com.yasir.erp.minierp.modules.supplier.application.converter.SupplierConverter;
import com.yasir.erp.minierp.modules.supplier.application.dto.SupplierDto;
import com.yasir.erp.minierp.modules.supplier.domain.model.Supplier;
import com.yasir.erp.minierp.modules.supplier.application.exception.SupplierNotFoundException;
import com.yasir.erp.minierp.modules.supplier.domain.port.inbound.command.ActivateSupplierUseCase;
import com.yasir.erp.minierp.modules.supplier.domain.port.outbound.command.SupplierCommandPort;
import com.yasir.erp.minierp.modules.supplier.domain.port.outbound.query.SupplierByIdAndActiveQueryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ActivateSupplierService implements ActivateSupplierUseCase {

    private final SupplierCommandPort commandPort;
    private final SupplierByIdAndActiveQueryPort byIdAndActivePort;
    private final SupplierConverter converter;

    public ActivateSupplierService(SupplierCommandPort commandPort,
                                   SupplierByIdAndActiveQueryPort byIdAndActivePort,
                                   SupplierConverter converter) {
        this.commandPort = commandPort;
        this.byIdAndActivePort = byIdAndActivePort;
        this.converter = converter;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public SupplierDto activateSupplier(String supplierId) {

        Supplier existing = byIdAndActivePort.findByIdAndActive(supplierId, false)
                .orElseThrow(() -> new SupplierNotFoundException(supplierId));

        Supplier updated = new Supplier(
                existing.getId(),
                existing.getName(),
                existing.getTaxNumber(),
                existing.getPhone(),
                existing.getEmail(),
                existing.getPurchaseOrders(),
                existing.getAddresses(),
                existing.getCreatedAt(),
                LocalDateTime.now(),
                true
        );
        return converter.convertToSupplierDto(commandPort.save(updated));
    }
}
