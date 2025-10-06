package com.yasir.erp.minierp.modules.payment.application.service.query;

import com.yasir.erp.minierp.modules.payment.application.exception.PaymentNotFoundException;
import com.yasir.erp.minierp.modules.payment.application.converter.PaymentConverter;
import com.yasir.erp.minierp.modules.payment.application.dto.PaymentDto;
import com.yasir.erp.minierp.modules.payment.domain.model.Payment;
import com.yasir.erp.minierp.modules.payment.domain.model.PaymentMethod;
import com.yasir.erp.minierp.modules.payment.domain.port.inbound.query.*;
import com.yasir.erp.minierp.modules.payment.domain.port.outbound.query.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Service
public class PaymentQueryService implements
        FindPaymentByIdUseCase,
        GetTotalPaidByCustomerUseCase,
        ListPaymentsByInvoiceUseCase,
        ListPaymentsByMethodUseCase,
        ListPaymentsByPaidAtBetweenUseCase,
        ListPaymentsByDueDateBetweenUseCase,
        ListPaymentsByCreatedAtBetweenUseCase,
        ListPaymentsByUpdatedAtBetweenUseCase,
        ListPaymentsByNoteUseCase,
        ListPaymentsByAmountUseCase,
        ListPaymentsByInvoiceAndMethodUseCase,
        ListPaymentsByMethodAndPaidAtBetweenUseCase {

    private final PaymentQueryPort paymentQueryPort;
    private final PaymentInvoiceQueryPort paymentInvoiceQueryPort;
    private final PaymentMethodQueryPort paymentMethodQueryPort;
    private final PaymentDateRangeQueryPort paymentDateRangeQueryPort;
    private final PaymentNoteQueryPort paymentNoteQueryPort;
    private final PaymentAmountQueryPort paymentAmountQueryPort;
    private final PaymentAggregatePort paymentAggregatePort;
    private final PaymentConverter paymentConverter;

    public PaymentQueryService(PaymentQueryPort paymentQueryPort,
                               PaymentInvoiceQueryPort paymentInvoiceQueryPort,
                               PaymentMethodQueryPort paymentMethodQueryPort,
                               PaymentDateRangeQueryPort paymentDateRangeQueryPort,
                               PaymentNoteQueryPort paymentNoteQueryPort,
                               PaymentAmountQueryPort paymentAmountQueryPort,
                               PaymentAggregatePort paymentAggregatePort,
                               PaymentConverter paymentConverter) {
        this.paymentQueryPort = paymentQueryPort;
        this.paymentInvoiceQueryPort = paymentInvoiceQueryPort;
        this.paymentMethodQueryPort = paymentMethodQueryPort;
        this.paymentDateRangeQueryPort = paymentDateRangeQueryPort;
        this.paymentNoteQueryPort = paymentNoteQueryPort;
        this.paymentAmountQueryPort = paymentAmountQueryPort;
        this.paymentAggregatePort = paymentAggregatePort;
        this.paymentConverter = paymentConverter;
    }


    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PaymentDto findDtoById(String paymentId) {
        return paymentConverter.convertToPaymentDto(findById(paymentId));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public BigDecimal getTotalPaidByCustomer(UUID customerId) {
        return sumPaidByCustomer(customerId);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<PaymentDto> findDtoAllByInvoiceId(String invoiceId) {
        return paymentConverter.convertToPaymentSetDto(findAllByInvoiceId(invoiceId));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<PaymentDto> findDtoAllByInvoiceIdAndMethod(String invoiceId, PaymentMethod method) {
        return paymentConverter.convertToPaymentSetDto(findAllByInvoiceIdAndMethod(invoiceId, method));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<PaymentDto> findDtoAllByMethod(PaymentMethod method) {
        return paymentConverter.convertToPaymentSetDto(findAllByMethod(method));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<PaymentDto> findDtoAllByMethodAndPaidAtBetween(PaymentMethod method, LocalDateTime start, LocalDateTime end) {
        return paymentConverter.convertToPaymentSetDto(findAllByMethodAndPaidAtBetween(method, start, end));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<PaymentDto> findDtoAllByPaidAtBetween(LocalDateTime start, LocalDateTime end) {
        return paymentConverter.convertToPaymentSetDto(findAllByPaidAtBetween(start, end));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<PaymentDto> findDtoAllByDueDateBetween(LocalDateTime start, LocalDateTime end) {
        return paymentConverter.convertToPaymentSetDto(findAllByDueDateBetween(start, end));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<PaymentDto> findDtoAllByCreatedAtBetween(LocalDateTime start, LocalDateTime end) {
        return paymentConverter.convertToPaymentSetDto(findAllByCreatedAtBetween(start, end));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<PaymentDto> findDtoAllByUpdatedAtBetween(LocalDateTime start, LocalDateTime end) {
        return paymentConverter.convertToPaymentSetDto(findAllByUpdatedAtBetween(start, end));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<PaymentDto> findDtoAllByNoteContainingIgnoreCase(String keyword) {
        return paymentConverter.convertToPaymentSetDto(findAllByNoteContainingIgnoreCase(keyword));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<PaymentDto> findDtoAllByAmountGreaterThanEqual(BigDecimal amount) {
        return paymentConverter.convertToPaymentSetDto(findAllByAmountGreaterThanEqual(amount));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<PaymentDto> findDtoAllByAmountLessThanEqual(BigDecimal amount) {
        return paymentConverter.convertToPaymentSetDto(findAllByAmountLessThanEqual(amount));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<PaymentDto> findDtoAllByAmountBetween(BigDecimal min, BigDecimal max) {
        return paymentConverter.convertToPaymentSetDto(findAllByAmountBetween(min, max));
    }


    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Payment findById(String paymentId) {
        return paymentQueryPort.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException(paymentId));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Set<Payment> findAllByInvoiceId(String invoiceId) {
        return paymentInvoiceQueryPort.findAllByInvoiceId(invoiceId);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Set<Payment> findAllByInvoiceIdAndMethod(String invoiceId, PaymentMethod method) {
        return paymentMethodQueryPort.findAllByInvoiceIdAndMethod(invoiceId,method);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Set<Payment> findAllByMethod(PaymentMethod method) {
        return paymentMethodQueryPort.findAllByMethod(method);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Set<Payment> findAllByMethodAndPaidAtBetween(PaymentMethod method, LocalDateTime start, LocalDateTime end) {
        return paymentMethodQueryPort.findAllByMethodAndPaidAtBetween(method, start, end);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Set<Payment> findAllByPaidAtBetween(LocalDateTime start, LocalDateTime end) {
        return paymentDateRangeQueryPort.findAllByPaidAtBetween(start, end);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Set<Payment> findAllByDueDateBetween(LocalDateTime start, LocalDateTime end) {
        return paymentDateRangeQueryPort.findAllByDueDateBetween(start, end);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Set<Payment> findAllByCreatedAtBetween(LocalDateTime start, LocalDateTime end) {
        return paymentDateRangeQueryPort.findAllByCreatedAtBetween(start, end);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Set<Payment> findAllByUpdatedAtBetween(LocalDateTime start, LocalDateTime end) {
        return paymentDateRangeQueryPort.findAllByUpdatedAtBetween(start, end);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Set<Payment> findAllByNoteContainingIgnoreCase(String keyword) {
        return paymentNoteQueryPort.findAllByNoteContainingIgnoreCase(keyword);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Set<Payment> findAllByAmountGreaterThanEqual(BigDecimal amount) {
        return paymentAmountQueryPort.findAllByAmountGreaterThanEqual(amount);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Set<Payment> findAllByAmountLessThanEqual(BigDecimal amount) {
        return paymentAmountQueryPort.findAllByAmountLessThanEqual(amount);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Set<Payment> findAllByAmountBetween(BigDecimal min, BigDecimal max) {
        return paymentAmountQueryPort.findAllByAmountBetween(min, max);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected BigDecimal sumPaidByCustomer(UUID customerId) {
        return paymentAggregatePort.sumPaidByCustomer(customerId);
    }
}
