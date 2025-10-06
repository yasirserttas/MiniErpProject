package com.yasir.erp.minierp.modules.address.infrastructure.persistence.adapter;

import com.yasir.erp.minierp.modules.address.domain.model.Address;
import com.yasir.erp.minierp.modules.address.domain.port.outbound.command.AddressCommandPort;
import com.yasir.erp.minierp.modules.address.domain.port.outbound.query.AddressQueryPort;
import com.yasir.erp.minierp.modules.address.domain.port.outbound.query.CustomerAddressQueryPort;
import com.yasir.erp.minierp.modules.address.domain.port.outbound.query.SupplierAddressQueryPort;
import com.yasir.erp.minierp.modules.address.domain.port.outbound.query.UserAddressQueryPort;
import com.yasir.erp.minierp.modules.address.infrastructure.persistence.AddressJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Component
public class AddressJpaAdapter implements AddressCommandPort,
        AddressQueryPort, CustomerAddressQueryPort ,
        SupplierAddressQueryPort, UserAddressQueryPort {

    private final AddressJpaRepository addressJpaRepository;

    public AddressJpaAdapter(AddressJpaRepository addressJpaRepository) {
        this.addressJpaRepository = addressJpaRepository;
    }

    @Override
    public Address save(Address address) {
        return addressJpaRepository.save(address);
    }

    @Override
    public void delete(Address address) {

        addressJpaRepository.delete(address);
    }

    @Override
    public Optional<Address> findById(String addressId) {
        return addressJpaRepository.findById(addressId);
    }

    @Override
    public Set<Address> findAllByCustomerIdAndActive(UUID customerId, boolean active) {
        return addressJpaRepository.findAllByCustomer_IdAndCustomer_Active(customerId, active);
    }

    @Override
    public Optional<Address> findByIdAndCustomerIdAndActive
            (String addressId, UUID customerId, boolean active) {
        return addressJpaRepository.
                findByIdAndCustomer_IdAndCustomer_Active(addressId,customerId,active);
    }

    @Override
    public boolean existsByIdAndCustomerIdAndActive
            (String addressId, UUID customerId, boolean active) {
        return addressJpaRepository.
                existsByIdAndCustomer_IdAndCustomer_Active(addressId,customerId,active);
    }

    @Override
    public Set<Address> findAllBySupplierIdAndActive(String supplierId, boolean active) {
        return addressJpaRepository.findAllBySupplier_IdAndSupplier_Active(supplierId,active);
    }

    @Override
    public Optional<Address> findByIdAndSupplierIdAndActive
            (String addressId, String supplierId, boolean active) {
        return addressJpaRepository.
                findByIdAndSupplier_IdAndSupplier_Active(addressId, supplierId, active);
    }

    @Override
    public boolean existsByIdAndSupplierIdAndActive
            (String addressId, String supplierId, boolean active) {
        return addressJpaRepository.
                existsByIdAndSupplier_IdAndSupplier_Active(addressId,supplierId,active);
    }

    @Override
    public Set<Address> findAllByUserIdAndActive(UUID userId, boolean active) {
        return addressJpaRepository.findAllByUser_IdAndUser_Active(userId,active);
    }

    @Override
    public Optional<Address> findByIdAndUserIdAndActive
            (String addressId, UUID userId, boolean active) {
        return addressJpaRepository.findByIdAndUser_IdAndUser_Active(addressId,userId,active);
    }

    @Override
    public boolean existsByIdAndUserIdAndActive(String addressId, UUID userId, boolean active) {
        return addressJpaRepository.existsByIdAndUser_IdAndUser_Active(addressId,userId,active);
    }
}
