package com.yasir.erp.minierp.modules.address.application.service.query;

import com.yasir.erp.minierp.modules.address.application.exception.AddressNotFoundException;
import com.yasir.erp.minierp.modules.address.application.converter.AddressConverter;
import com.yasir.erp.minierp.modules.address.application.dto.AddressDto;
import com.yasir.erp.minierp.modules.address.domain.model.Address;
import com.yasir.erp.minierp.modules.address.domain.port.outbound.query.AddressQueryPort;
import com.yasir.erp.minierp.modules.address.domain.port.outbound.query.CustomerAddressQueryPort;
import com.yasir.erp.minierp.modules.address.domain.port.outbound.query.SupplierAddressQueryPort;
import com.yasir.erp.minierp.modules.address.domain.port.outbound.query.UserAddressQueryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Service
public class AddressQueryService {

    private final AddressQueryPort addressQueryPort;
    private final CustomerAddressQueryPort customerAddressQueryPort;
    private final UserAddressQueryPort userAddressQueryPort;
    private final SupplierAddressQueryPort supplierAddressQueryPort;
    private final AddressConverter addressConverter;

    public AddressQueryService(AddressQueryPort addressQueryPort,
                               CustomerAddressQueryPort customerAddressQueryPort,
                               UserAddressQueryPort userAddressQueryPort,
                               SupplierAddressQueryPort supplierAddressQueryPort,
                               AddressConverter addressConverter) {
        this.addressQueryPort = addressQueryPort;
        this.customerAddressQueryPort = customerAddressQueryPort;
        this.userAddressQueryPort = userAddressQueryPort;
        this.supplierAddressQueryPort = supplierAddressQueryPort;
        this.addressConverter = addressConverter;
    }


    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public AddressDto findDtoById(String addressId) {

        return addressConverter.convertToAddress(findById(addressId));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<AddressDto> findDtoAllByCustomer_IdAndCustomer_Active(
            UUID customerId, boolean active) {
        return addressConverter.
                convertToAddressSet(findAllByCustomer_IdAndCustomer_Active(customerId, active));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<AddressDto> findDtoAllByUser_IdAndUser_Active(UUID userId, boolean active) {
        return addressConverter.
                convertToAddressSet(findAllByUser_IdAndUser_Active(userId, active));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<AddressDto> findDtoAllBySupplier_IdAndSupplier_Active
            (String supplierId, boolean active) {
        return addressConverter.convertToAddressSet
                (findAllBySupplier_IdAndSupplier_Active(supplierId, active));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public AddressDto findDtoByIdAndCustomerIdAndCustomerActive
            (String addressId, UUID customerId, boolean customerActive) {
        return addressConverter.convertToAddress
                (findByIdAndCustomerIdAndCustomerActive(addressId, customerId, customerActive));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public AddressDto findDtoByIdAndUserIdAndUserActive
            (String addressId, UUID userId, boolean userActive) {
        return addressConverter.convertToAddress
                (findByIdAndUserIdAndUserActive(addressId, userId, userActive));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public AddressDto findDtoByIdAndSupplierIdAndSupplierActive
            (String addressId, String supplierId, boolean supplierActive) {
        return addressConverter.
                convertToAddress
                        (findByIdAndSupplierIdAndSupplierActive
                                (addressId, supplierId, supplierActive));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Address findById(String addressId) {
        return addressQueryPort.findById(addressId)
                .orElseThrow(() -> new AddressNotFoundException(addressId));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Set<Address> findAllByCustomer_IdAndCustomer_Active(UUID customerId, boolean active) {
        return customerAddressQueryPort.findAllByCustomerIdAndActive(customerId, active);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Set<Address> findAllByUser_IdAndUser_Active(UUID userId, boolean active) {
        return userAddressQueryPort.findAllByUserIdAndActive(userId, active);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Set<Address> findAllBySupplier_IdAndSupplier_Active
            (String supplierId, boolean active) {
        return supplierAddressQueryPort.findAllBySupplierIdAndActive(supplierId, active);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Address findByIdAndCustomerIdAndCustomerActive
            (String addressId, UUID customerId, boolean customerActive) {
        return customerAddressQueryPort.
                findByIdAndCustomerIdAndActive(addressId, customerId, customerActive)
                .orElseThrow(() -> new AddressNotFoundException(addressId));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Address findByIdAndUserIdAndUserActive
            (String addressId, UUID userId, boolean userActive) {
        return userAddressQueryPort.findByIdAndUserIdAndActive(addressId, userId, userActive)
                .orElseThrow(() -> new AddressNotFoundException(addressId));
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    protected Address findByIdAndSupplierIdAndSupplierActive
            (String addressId, String supplierId, boolean supplierActive) {
        return supplierAddressQueryPort.
                findByIdAndSupplierIdAndActive(addressId, supplierId, supplierActive)
                .orElseThrow(() -> new AddressNotFoundException(addressId));
    }
}
