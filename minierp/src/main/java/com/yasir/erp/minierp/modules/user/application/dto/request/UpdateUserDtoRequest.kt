package com.yasir.erp.minierp.modules.user.application.dto.request

import com.yasir.erp.minierp.modules.user.domain.model.Role
import java.util.UUID

data class UpdateUserDtoRequest(
    val id: UUID,
    val name: String,
    val surName: String,
    val username: String,
    val companyName: String,
    val phoneNumber: String,
    val email: String,
    val taxNumber: String,
    val roles: Set<Role>
)
