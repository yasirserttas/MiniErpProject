package com.yasir.erp.minierp.modules.address.domain.port.outbound.command;


import com.yasir.erp.minierp.modules.address.domain.model.Address;

public interface AddressCommandPort {
    Address save(Address address);
    void delete(Address address);
}
