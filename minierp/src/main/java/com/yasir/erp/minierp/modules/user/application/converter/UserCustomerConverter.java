package com.yasir.erp.minierp.modules.user.application.converter;

import com.yasir.erp.minierp.modules.user.application.dto.UserCustomerDto;
import com.yasir.erp.minierp.modules.user.domain.model.User;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserCustomerConverter {

    public UserCustomerDto convertToUserCustomerDto(User user) {
        return new UserCustomerDto(
                user.getId(),
                user.getName(),
                user.getSurName(),
                user.getUsername(),
                user.getCompanyName(),
                user.getEmail(),
                user.getActive()
        );
    }

    public Set<UserCustomerDto> convertToSetUserCustomerDto(Set<User> users) {
        return users.stream()
                .map(this::convertToUserCustomerDto)
                .collect(Collectors.toSet());
    }
}
