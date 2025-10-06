package com.yasir.erp.minierp.modules.user.application.converter;

import com.yasir.erp.minierp.modules.user.application.dto.UserDispatchNoteDto;
import com.yasir.erp.minierp.modules.user.domain.model.User;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserDispatchNoteConverter {

    public UserDispatchNoteDto convertToUserDispatchNoteDto(User user) {
        return new UserDispatchNoteDto(
                user.getId(),
                user.getName(),
                user.getSurName(),
                user.getEmail(),
                user.getActive()
        );
    }

    public Set<UserDispatchNoteDto> convertToSetUserDispatchNoteDto(Set<User> users) {
        return users.stream()
                .map(this::convertToUserDispatchNoteDto)
                .collect(Collectors.toSet());
    }
}
