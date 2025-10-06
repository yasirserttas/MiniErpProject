package com.yasir.erp.minierp.modules.address.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.address.domain.model.Address;

import java.util.Optional;
import java.util.Set;

public interface SupplierAddressQueryPort {
    Set<Address> findAllBySupplierIdAndActive(String supplierId, boolean active);
    Optional<Address> findByIdAndSupplierIdAndActive
            (String addressId, String supplierId, boolean active);
    boolean existsByIdAndSupplierIdAndActive(String addressId, String supplierId, boolean active);
}
