package com.yasir.erp.minierp.modules.cheque.domain.port.outbound.command;

import com.yasir.erp.minierp.modules.cheque.domain.model.Cheque;

public interface ChequeCommandPort {
    Cheque save(Cheque cheque);
    void delete(Cheque cheque);
}
