package com.yasir.erp.minierp.modules.receipt.domain.port.inbound.command;

import com.yasir.erp.minierp.modules.receipt.application.dto.ReceiptDto;
import com.yasir.erp.minierp.modules.receipt.application.dto.request.CreateReceiptDtoRequest;

public interface CreateReceiptUseCase {
    ReceiptDto createReceipt(CreateReceiptDtoRequest request);
}