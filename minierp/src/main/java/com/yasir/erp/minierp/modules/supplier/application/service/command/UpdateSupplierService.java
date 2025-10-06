package com.yasir.erp.minierp.modules.supplier.application.service.command;

import com.yasir.erp.minierp.modules.supplier.application.converter.SupplierConverter;
import com.yasir.erp.minierp.modules.supplier.application.dto.SupplierDto;
import com.yasir.erp.minierp.modules.supplier.application.dto.request.UpdateSupplierDtoRequest;
import com.yasir.erp.minierp.modules.supplier.domain.model.Supplier;
import com.yasir.erp.minierp.modules.supplier.application.exception.SupplierNotFoundException;
import com.yasir.erp.minierp.modules.supplier.domain.port.inbound.command.UpdateSupplierUseCase;
import com.yasir.erp.minierp.modules.supplier.domain.port.outbound.command.SupplierCommandPort;
import com.yasir.erp.minierp.modules.supplier.domain.port.outbound.query.SupplierByIdAndActiveQueryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UpdateSupplierService implements UpdateSupplierUseCase {

    private final SupplierCommandPort commandPort;
    private final SupplierByIdAndActiveQueryPort byIdAndActivePort;
    private final SupplierConverter converter;

    public UpdateSupplierService(SupplierCommandPort commandPort,
                                 SupplierByIdAndActiveQueryPort byIdAndActivePort,
                                 SupplierConverter converter) {
        this.commandPort = commandPort;
        this.byIdAndActivePort = byIdAndActivePort;
        this.converter = converter;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public SupplierDto updateSupplier(UpdateSupplierDtoRequest req) {

        Supplier existing = requireByIdAndActive(req.getId(), true);

        Supplier updated = new Supplier(
                existing.getId(),
                req.getName(),
                req.getTaxNumber(),
                req.getPhone(),
                req.getEmail(),
                existing.getPurchaseOrders(),
                existing.getAddresses(),
                existing.getCreatedAt(),
                LocalDateTime.now(),
                true
        );
        return converter.convertToSupplierDto(commandPort.save(updated));
    }

    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    protected Supplier requireByIdAndActive(String id, boolean active) {
        return byIdAndActivePort.findByIdAndActive(id, active)
                .orElseThrow(() -> new SupplierNotFoundException(id));
    }
}
