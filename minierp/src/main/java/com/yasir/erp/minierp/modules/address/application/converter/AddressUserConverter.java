package com.yasir.erp.minierp.modules.address.application.converter;

import com.yasir.erp.minierp.modules.user.application.converter.UserAddressConverter;
import com.yasir.erp.minierp.modules.address.application.dto.AddressUserDto;
import com.yasir.erp.minierp.modules.address.domain.model.Address;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AddressUserConverter {

    private final UserAddressConverter addressConverter;

    public AddressUserConverter(UserAddressConverter addressConverter) {
        this.addressConverter = addressConverter;
    }

    public AddressUserDto convertToSetAddressUser(Address address){

        return new AddressUserDto(
                address.getId(),
                address.getStreet(),
                address.getDistrict(),
                address.getCity(),
                address.getCountry(),
                address.getPostalCode(),
                addressConverter.convertToUserAddress(address.getUser())
        );
    }

    public Set<AddressUserDto> convertToSetAddressUser(Set<Address> addresses){

        return addresses.stream().map(this::convertToSetAddressUser).collect(Collectors.toSet());

    }

}
