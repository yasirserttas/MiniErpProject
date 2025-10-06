package com.yasir.erp.minierp.modules.cheque.domain.port.outbound.query;

public interface ChequeExistsQueryPort {
    boolean existsByChequeNumber(String chequeNumber);
}
