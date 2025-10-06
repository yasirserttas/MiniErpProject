package com.yasir.erp.minierp.modules.user.application.service.command;

import com.yasir.erp.minierp.modules.user.application.converter.UserConverter;
import com.yasir.erp.minierp.modules.user.application.dto.UserDto;
import com.yasir.erp.minierp.modules.user.application.dto.request.CreateUserDtoRequest;
import com.yasir.erp.minierp.modules.user.domain.model.User;
import com.yasir.erp.minierp.modules.user.domain.port.inbound.command.AddUserUseCase;
import com.yasir.erp.minierp.modules.user.domain.port.outbound.command.UserCommandPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.UUID;

@Service
public class AddUserService implements AddUserUseCase {

    private final UserCommandPort commandPort;
    private final UserConverter converter;

    public AddUserService(UserCommandPort commandPort, UserConverter converter) {
        this.commandPort = commandPort;
        this.converter = converter;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public UserDto addUser(CreateUserDtoRequest createUserDtoRequest) {
        User user = new User(
                UUID.randomUUID(),
                createUserDtoRequest.getName(),
                createUserDtoRequest.getSurName(),
                createUserDtoRequest.getCompanyName(),
                createUserDtoRequest.getPhoneNumber(),
                createUserDtoRequest.getUsername(),
                createUserDtoRequest.getEmail(),
                createUserDtoRequest.getPassword(),
                createUserDtoRequest.getTaxNumber(),
                LocalDateTime.now(),
                new HashSet<>(),
                new HashSet<>(),
                new HashSet<>(),
                new HashSet<>(),
                new HashSet<>(),
                createUserDtoRequest.getRoles(),
                true
        );
        return converter.convertToUser(commandPort.save(user));
    }
}
