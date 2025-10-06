package com.yasir.erp.minierp.modules.cheque.domain.port.inbound.command;

public interface HardDeleteChequeUseCase {
    void hardDelete(String chequeId);
}
