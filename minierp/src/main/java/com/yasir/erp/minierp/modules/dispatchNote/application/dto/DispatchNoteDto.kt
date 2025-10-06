package com.yasir.erp.minierp.dto.dispatchNote

import com.yasir.erp.minierp.modules.address.application.dto.AddressDto
import com.yasir.erp.minierp.modules.customer.application.dto.CustomerDispatchNoteDto
import com.yasir.erp.minierp.modules.order.application.dto.OrderDispatchNoteDto
import com.yasir.erp.minierp.modules.user.application.dto.UserDispatchNoteDto

import com.yasir.erp.minierp.modules.dispatchNote.domain.model.DispatchNoteStatus
import java.time.LocalDateTime

data class DispatchNoteDto(
    val id: String,
    val user: UserDispatchNoteDto,
    val customer: CustomerDispatchNoteDto,
    val order: OrderDispatchNoteDto?,   // sipariş opsiyonel
    val deliveryAddress: AddressDto,
    val transporterName: String?,
    val transporterPlate: String?,
    val dispatchDate: LocalDateTime,
    val estimatedArrival: LocalDateTime?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val dispatchNoteStatus: DispatchNoteStatus,
    val dispatchNoteNumber: String,
    val deliveredBy: String?,
    val receivedBy: String?,
    val active: Boolean
)