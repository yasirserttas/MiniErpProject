package com.yasir.erp.minierp.modules.cheque.infrastructure.persistence;

import com.yasir.erp.minierp.modules.cheque.domain.model.Cheque;
import com.yasir.erp.minierp.modules.cheque.domain.model.ChequeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ChequeJpaRepository extends JpaRepository<Cheque, String> {

    boolean existsByChequeNumber(String chequeNumber);

    Optional<Cheque> findByIdAndActive(String id, boolean active);
    Optional<Cheque> findByChequeNumberAndActive(String chequeNumber, boolean active);

    Set<Cheque> findAllByActive(boolean active);
    Set<Cheque> findAllByStatusAndActive(ChequeStatus status, boolean active);

    Set<Cheque> findAllByBankAccount_IdAndActive(String bankAccountId, boolean active);
    Set<Cheque> findAllByStatusAndBankAccount_IdAndActive
            (ChequeStatus status, String bankAccountId, boolean active);

    Set<Cheque> findAllByDueDateBeforeAndStatusAndActive
    (LocalDateTime now, ChequeStatus status, boolean active);
    Set<Cheque> findAllByDueDateBetweenAndStatusAndActive
            (LocalDateTime start, LocalDateTime end, ChequeStatus status, boolean active);

    Set<Cheque> findAllByCreatedAtBetweenAndActive
            (LocalDateTime start, LocalDateTime end, boolean active);
}
