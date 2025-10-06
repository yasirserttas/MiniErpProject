package com.yasir.erp.minierp.modules.cheque.infrastructure.persistence.adapter;

import com.yasir.erp.minierp.modules.cheque.domain.model.Cheque;
import com.yasir.erp.minierp.modules.cheque.domain.model.ChequeStatus;
import com.yasir.erp.minierp.modules.cheque.domain.port.outbound.command.ChequeCommandPort;
import com.yasir.erp.minierp.modules.cheque.domain.port.outbound.query.*;
import com.yasir.erp.minierp.modules.cheque.infrastructure.persistence.ChequeJpaRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Component
public class ChequeJpaAdapter implements ChequeCommandPort, ChequeActiveQueryPort,
        ChequeBankAccountQueryPort, ChequeCreatedAtQueryPort, ChequeDueDateQueryPort ,
        ChequeExistsQueryPort,ChequeQueryPort,ChequeStatusQueryPort {

    private final ChequeJpaRepository chequeJpaRepository;

    public ChequeJpaAdapter(ChequeJpaRepository chequeJpaRepository) {
        this.chequeJpaRepository = chequeJpaRepository;
    }

    @Override
    public Cheque save(Cheque cheque) {
        return chequeJpaRepository.save(cheque);
    }

    @Override
    public void delete(Cheque cheque) {
        chequeJpaRepository.delete(cheque);
    }

    @Override
    public Set<Cheque> findAllByActive(boolean active) {
        return chequeJpaRepository.findAllByActive(active);
    }

    @Override
    public Set<Cheque> findAllByBankAccountIdAndActive(String bankAccountId, boolean active) {
        return chequeJpaRepository.findAllByBankAccount_IdAndActive(bankAccountId,active);
    }

    @Override
    public Set<Cheque> findAllByCreatedAtBetweenAndActive
            (LocalDateTime start, LocalDateTime end, boolean active) {
        return chequeJpaRepository.findAllByCreatedAtBetweenAndActive(start, end, active);
    }

    @Override
    public Set<Cheque> findAllByDueDateBeforeAndStatusAndActive
            (LocalDateTime now, ChequeStatus status, boolean active) {
        return chequeJpaRepository.findAllByDueDateBeforeAndStatusAndActive(now,status,active);
    }

    @Override
    public Set<Cheque> findAllByDueDateBetweenAndStatusAndActive
            (LocalDateTime start, LocalDateTime end, ChequeStatus status, boolean active) {
        return chequeJpaRepository.
                findAllByDueDateBetweenAndStatusAndActive(start,end,status,active);
    }

    @Override
    public boolean existsByChequeNumber(String chequeNumber) {
        return chequeJpaRepository.existsByChequeNumber(chequeNumber);
    }

    @Override
    public Optional<Cheque> findByIdAndActive(String id, boolean active) {
        return chequeJpaRepository.findByIdAndActive(id,active);
    }

    @Override
    public Optional<Cheque> findByChequeNumberAndActive(String chequeNumber, boolean active) {
        return chequeJpaRepository.findByChequeNumberAndActive(chequeNumber,active);
    }

    @Override
    public Set<Cheque> findAllByStatusAndActive(ChequeStatus status, boolean active) {
        return chequeJpaRepository.findAllByStatusAndActive(status,active);
    }

    @Override
    public Set<Cheque> findAllByStatusAndBankAccountIdAndActive
            (ChequeStatus status, String bankAccountId, boolean active) {
        return chequeJpaRepository.findAllByStatusAndBankAccount_IdAndActive
                (status,bankAccountId,active);
    }
}
