package com.yasir.erp.minierp.modules.user.application.service.command;

import com.yasir.erp.minierp.modules.user.application.exception.UserNotFoundException;
import com.yasir.erp.minierp.modules.user.domain.model.User;
import com.yasir.erp.minierp.modules.user.domain.port.inbound.command.DeleteUserByIdUseCase;
import com.yasir.erp.minierp.modules.user.domain.port.outbound.command.UserCommandPort;
import com.yasir.erp.minierp.modules.user.domain.port.outbound.query.UserByIdQueryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class DeleteUserByIdService implements DeleteUserByIdUseCase {

    private final UserCommandPort commandPort;
    private final UserByIdQueryPort byIdPort;

    public DeleteUserByIdService(UserCommandPort commandPort, UserByIdQueryPort byIdPort) {
        this.commandPort = commandPort;
        this.byIdPort = byIdPort;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteUserById(UUID userId) {

        User user = byIdPort.findById(userId).orElseThrow(()->
                new UserNotFoundException(userId));
        commandPort.delete(user);
    }
}
