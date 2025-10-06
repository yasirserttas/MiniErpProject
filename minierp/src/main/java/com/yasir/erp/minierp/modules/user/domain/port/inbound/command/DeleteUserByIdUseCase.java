package com.yasir.erp.minierp.modules.user.domain.port.inbound.command;

import java.util.UUID;

public interface DeleteUserByIdUseCase {
    void deleteUserById(UUID userId);
}