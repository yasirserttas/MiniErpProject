package com.yasir.erp.minierp.modules.user.application.converter;

import com.yasir.erp.minierp.modules.user.application.dto.UserInvoiceDto;
import com.yasir.erp.minierp.modules.user.domain.model.User;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserInvoiceConverter {

    public UserInvoiceDto convertToUserInvoiceDto(User user) {
        return new UserInvoiceDto(
                user.getId(),
                user.getName(),
                user.getSurName(),
                user.getEmail(),
                user.getActive()
        );
    }

    public Set<UserInvoiceDto> convertToSetUserInvoiceDto(Set<User> users) {
        return users.stream()
                .map(this::convertToUserInvoiceDto)
                .collect(Collectors.toSet());
    }
}
