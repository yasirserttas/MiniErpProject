package com.yasir.erp.minierp.modules.address.application.service.command;

import com.yasir.erp.minierp.modules.address.application.exception.AddressNotFoundException;
import com.yasir.erp.minierp.modules.address.domain.model.Address;
import com.yasir.erp.minierp.modules.address.domain.port.inbound.command.DeleteAddressUseCase;
import com.yasir.erp.minierp.modules.address.domain.port.outbound.command.AddressCommandPort;
import com.yasir.erp.minierp.modules.address.domain.port.outbound.query.AddressQueryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteAddressService implements DeleteAddressUseCase {

    private final AddressCommandPort addressCommandPort;
    private final AddressQueryPort addressQueryPort;

    public DeleteAddressService(AddressCommandPort addressCommandPort,
                                AddressQueryPort addressQueryPort) {
        this.addressCommandPort = addressCommandPort;
        this.addressQueryPort = addressQueryPort;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteAddress(String addressId) {

        Address address = addressQueryPort.findById(addressId)
                .orElseThrow(()-> new AddressNotFoundException(addressId));

        addressCommandPort.delete(address);
    }
}
