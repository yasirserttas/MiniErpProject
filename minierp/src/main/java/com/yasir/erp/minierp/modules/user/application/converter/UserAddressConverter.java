package com.yasir.erp.minierp.modules.user.application.converter;

import com.yasir.erp.minierp.modules.user.application.dto.UserAddressDto;
import com.yasir.erp.minierp.modules.user.domain.model.User;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserAddressConverter {

    public UserAddressDto convertToUserAddress(User user){
        return new UserAddressDto(
                user.getId(),
                user.getUsername(),
                user.getCompanyName(),
                user.getName(),
                user.getSurName()
        );
    }

    public Set<UserAddressDto> convertToSetUserAddress(Set<User> users){
        return users.stream().map(this::convertToUserAddress).collect(Collectors.toSet());
    }
}
