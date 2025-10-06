package com.yasir.erp.minierp.modules.address.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.address.application.dto.AddressDto;

public interface FindAddressByIdUseCase {
    AddressDto findById(String id);
}
