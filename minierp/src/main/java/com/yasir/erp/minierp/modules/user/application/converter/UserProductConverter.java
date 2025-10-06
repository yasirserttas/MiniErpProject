package com.yasir.erp.minierp.modules.user.application.converter;

import com.yasir.erp.minierp.modules.user.application.dto.UserProductDto;
import com.yasir.erp.minierp.modules.user.domain.model.User;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserProductConverter {

    public UserProductDto convertToUserProductDto(User user) {
        return new UserProductDto(
                user.getId(),
                user.getName(),
                user.getSurName(),
                user.getUsername(),
                user.getCompanyName(),
                user.getPhoneNumber(),
                user.getEmail()
        );
    }

    public Set<UserProductDto> convertToSetUserProductDto(Set<User> users) {
        return users.stream()
                .map(this::convertToUserProductDto)
                .collect(Collectors.toSet());
    }
}
