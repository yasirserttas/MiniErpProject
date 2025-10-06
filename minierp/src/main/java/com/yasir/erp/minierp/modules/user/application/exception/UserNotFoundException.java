package com.yasir.erp.minierp.modules.user.application.exception;

import com.yasir.erp.minierp.common.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends BusinessException {

    public UserNotFoundException(UUID userId) {
        super("USER_NOT_FOUND",
                String.format("User not found. ID[%s]",userId.toString()));
    }
}
