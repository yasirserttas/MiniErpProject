package com.yasir.erp.minierp.modules.receipt.application.service.query;

import com.yasir.erp.minierp.modules.receipt.application.converter.ReceiptConverter;
import com.yasir.erp.minierp.modules.receipt.application.dto.ReceiptDto;
import com.yasir.erp.minierp.modules.receipt.domain.model.Receipt;
import com.yasir.erp.minierp.modules.receipt.domain.port.inbound.query.*;
import com.yasir.erp.minierp.modules.receipt.domain.port.outbound.query.*;
import com.yasir.erp.minierp.modules.receipt.application.exception.ReceiptNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;

@Service
public class ReceiptQueryService implements
        FindReceiptByIdUseCase,
        FindReceiptByNumberUseCase,
        FindReceiptByPaymentUseCase,
        ListReceiptsByActiveUseCase,
        ListReceiptsByDateRangeUseCase,
        ListReceiptsByIssuedByAndDateUseCase,
        ListReceiptsByIssuedByUseCase,
        ListReceiptsByPaymentUseCase,
        ListReceiptsByReceivedByUseCase,
        ListReceiptsUseCase {

    private final ReceiptConverter converter;
    private final ReceiptQueryPort baseQueryPort;
    private final ReceiptActiveQueryPort activeQueryPort;
    private final ReceiptPaymentQueryPort paymentQueryPort;
    private final ReceiptDateQueryPort receiptDateQueryPort;
    private final ReceiptTextSearchQueryPort receiptTextSearchQueryPort;

    public ReceiptQueryService(ReceiptConverter converter,
                               ReceiptQueryPort baseQueryPort,
                               ReceiptActiveQueryPort activeQueryPort,
                               ReceiptPaymentQueryPort paymentQueryPort,
                               ReceiptDateQueryPort receiptDateQueryPort,
                               ReceiptTextSearchQueryPort receiptTextSearchQueryPort) {
        this.converter = converter;
        this.baseQueryPort = baseQueryPort;
        this.activeQueryPort = activeQueryPort;
        this.paymentQueryPort = paymentQueryPort;
        this.receiptDateQueryPort = receiptDateQueryPort;
        this.receiptTextSearchQueryPort = receiptTextSearchQueryPort;
    }


    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public ReceiptDto findById(String id, boolean active) {
        return converter.convertToReceiptDto(findEntityById(id, active));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public ReceiptDto findByNumber(String receiptNumber, boolean active) {
        return converter.convertToReceiptDto(findEntityByNumber(receiptNumber, active));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public ReceiptDto findByPaymentId(String paymentId, boolean active) {
        return converter.convertToReceiptDto(findEntityByPaymentId(paymentId, active));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<ReceiptDto> findAllByActiveStatus(boolean active) {
        return converter.convertToReceiptSetDto(findEntitiesByActive(active));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<ReceiptDto> findByDateRangeAndActiveStatus(LocalDateTime start, LocalDateTime end, boolean active) {
        return converter.convertToReceiptSetDto(findEntitiesByDateRange(start, end, active));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<ReceiptDto> findAllByIssuerAndDateRangeAndStatus(String issuedBy, LocalDateTime start, LocalDateTime end, boolean active) {
        return converter.convertToReceiptSetDto(findEntitiesByIssuerAndDateRange(issuedBy, start, end, active));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<ReceiptDto> findAllByIssuerAndStatus(String issuedBy, boolean active) {
        return converter.convertToReceiptSetDto(findEntitiesByIssuer(issuedBy, active));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<ReceiptDto> findAllByPaymentIdAndStatus(String paymentId, boolean active) {
        return converter.convertToReceiptSetDto(findEntitiesByPayment(paymentId, active));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<ReceiptDto> findAllByReceiverAndStatus(String receivedBy, boolean active) {
        return converter.convertToReceiptSetDto(findEntitiesByReceiver(receivedBy, active));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<ReceiptDto> findAll() {
        return converter.convertToReceiptSetDto(findEntitiesAll());
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Receipt findEntityById(String id, boolean active) {
        return baseQueryPort.findByIdAndActive(id, active)
                .orElseThrow(() -> new ReceiptNotFoundException(id));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Receipt findEntityByNumber(String receiptNumber, boolean active) {
        return baseQueryPort.findByReceiptNumberAndActive(receiptNumber, active)
                .orElseThrow(() -> new ReceiptNotFoundException(receiptNumber));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Receipt findEntityByPaymentId(String paymentId, boolean active) {
        return baseQueryPort.findByPaymentIdAndActive(paymentId, active)
                .orElseThrow(() -> new ReceiptNotFoundException(paymentId));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Set<Receipt> findEntitiesByActive(boolean active) {
        return activeQueryPort.findAllByActive(active);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    protected Set<Receipt> findEntitiesByDateRange
            (LocalDateTime start, LocalDateTime end, boolean active) {
        return receiptDateQueryPort.findAllByReceiptDateBetweenAndActive(start, end, active);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    protected Set<Receipt> findEntitiesByIssuerAndDateRange
            (String issuedBy, LocalDateTime start, LocalDateTime end, boolean active) {
        return receiptTextSearchQueryPort.findAllByIssuedByAndDateBetweenAndActive
                (issuedBy, start, end, active);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    protected Set<Receipt> findEntitiesByIssuer(String issuedBy, boolean active) {
        return receiptTextSearchQueryPort.findAllByIssuedByAndActive(issuedBy, active);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    protected Set<Receipt> findEntitiesByPayment(String paymentId, boolean active) {
        return paymentQueryPort.findAllByPaymentIdAndActive(paymentId, active);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    protected Set<Receipt> findEntitiesByReceiver(String receivedBy, boolean active) {
        return receiptTextSearchQueryPort.findAllByReceivedByAndActive(receivedBy, active);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    protected Set<Receipt> findEntitiesAll() {
        return activeQueryPort.findAll();
    }
}
