package com.yasir.erp.minierp.modules.user.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.user.application.dto.UserDto;
import java.util.UUID;

public interface FindUserByIdAndActiveUseCase {
    UserDto findDtoByIdAndActive(UUID userId, Boolean active);
}