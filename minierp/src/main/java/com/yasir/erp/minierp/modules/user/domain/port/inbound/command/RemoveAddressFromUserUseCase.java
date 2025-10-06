package com.yasir.erp.minierp.modules.user.domain.port.inbound.command;

import com.yasir.erp.minierp.modules.user.application.dto.UserDto;
import java.util.UUID;

public interface RemoveAddressFromUserUseCase {
    UserDto removeAddressFromUser(UUID userId, String addressId);
}