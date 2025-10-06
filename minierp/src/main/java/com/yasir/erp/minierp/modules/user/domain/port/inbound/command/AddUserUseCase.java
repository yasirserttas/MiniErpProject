package com.yasir.erp.minierp.modules.user.domain.port.inbound.command;

import com.yasir.erp.minierp.modules.user.application.dto.UserDto;
import com.yasir.erp.minierp.modules.user.application.dto.request.CreateUserDtoRequest;

public interface AddUserUseCase {
    UserDto addUser(CreateUserDtoRequest req);
}