package com.yasir.erp.minierp.modules.user.application.converter;

import com.yasir.erp.minierp.modules.user.application.dto.UserOrderDto;
import com.yasir.erp.minierp.modules.user.domain.model.User;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserOrderConverter {

    public UserOrderDto convertToUserOrderDto(User user) {
        return new UserOrderDto(
                user.getId(),
                user.getName(),
                user.getSurName(),
                user.getUsername(),
                user.getEmail(),
                user.getCompanyName(),
                user.getActive()
        );
    }

    public Set<UserOrderDto> convertToSetUserOrderDto(Set<User> users) {
        return users.stream()
                .map(this::convertToUserOrderDto)
                .collect(Collectors.toSet());
    }
}
