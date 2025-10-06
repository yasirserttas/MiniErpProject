package com.yasir.erp.minierp.modules.supplier.application.service.command;

import com.yasir.erp.minierp.common.generator.UlidGenerator;
import com.yasir.erp.minierp.modules.supplier.application.converter.SupplierConverter;
import com.yasir.erp.minierp.modules.supplier.application.dto.SupplierDto;
import com.yasir.erp.minierp.modules.supplier.application.dto.request.CreateSupplierDtoRequest;
import com.yasir.erp.minierp.modules.supplier.domain.model.Supplier;
import com.yasir.erp.minierp.modules.supplier.domain.port.inbound.command.AddSupplierUseCase;
import com.yasir.erp.minierp.modules.supplier.domain.port.outbound.command.SupplierCommandPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;

@Service
public class AddSupplierService implements AddSupplierUseCase {

    private final SupplierCommandPort commandPort;
    private final SupplierConverter converter;

    public AddSupplierService(SupplierCommandPort commandPort, SupplierConverter converter) {
        this.commandPort = commandPort;
        this.converter = converter;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public SupplierDto addSupplier(CreateSupplierDtoRequest req) {

        Supplier supplier = new Supplier(
                UlidGenerator.generate(),
                req.getName(),
                req.getTaxNumber(),
                req.getPhone(),
                req.getEmail(),
                new HashSet<>(),
                new HashSet<>(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                true
        );
        return converter.convertToSupplierDto(commandPort.save(supplier));
    }
}
