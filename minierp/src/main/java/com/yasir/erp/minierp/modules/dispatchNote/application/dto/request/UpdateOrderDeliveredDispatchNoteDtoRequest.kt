package com.yasir.erp.minierp.dto.dispatchNote.request

import com.yasir.erp.minierp.modules.address.application.dto.request.customer.CreateCustomerAddressDtoRequest

data class UpdateOrderDeliveredDispatchNoteDtoRequest(

    val dispatchNoteId: String,
    val deliveryAddress: CreateCustomerAddressDtoRequest,
    val receivedBy: String?

)