package com.yasir.erp.minierp.modules.address.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.address.application.dto.AddressDto;

import java.util.Set;

public interface ListAddressesBySupplierUseCase {
    Set<AddressDto> list(String supplierId, boolean active);
}
