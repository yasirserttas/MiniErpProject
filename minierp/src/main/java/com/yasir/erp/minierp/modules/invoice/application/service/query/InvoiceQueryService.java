package com.yasir.erp.minierp.modules.invoice.application.service.query;

import com.yasir.erp.minierp.modules.invoice.domain.port.outbound.query.InvoiceAmountQueryPort;
import com.yasir.erp.minierp.modules.invoice.application.converter.InvoiceConverter;
import com.yasir.erp.minierp.modules.invoice.application.dto.InvoiceDto;
import com.yasir.erp.minierp.modules.invoice.domain.model.Invoice;
import com.yasir.erp.minierp.modules.invoice.domain.port.inbound.query.*;
import com.yasir.erp.minierp.modules.invoice.domain.port.outbound.query.*;
import com.yasir.erp.minierp.modules.invoice.application.exception.InvoiceNotFoundException;
import com.yasir.erp.minierp.modules.invoice.application.exception.InvoiceNumberNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class InvoiceQueryService implements
        FindInvoiceByIdUseCase,
        FindInvoiceByNumberUseCase,
        FindInvoiceByOrderUseCase,
        ListInvoicesByActiveUseCase,
        ListInvoicesByAmountMinUseCase,
        ListInvoicesByCreatedAtUseCase,
        ListInvoicesByCustomerAndDateUseCase,
        ListInvoicesByCustomerUseCase,
        ListInvoicesByIssuedByUseCase,
        ListInvoicesByReceivedByUseCase,
        ListInvoicesByUserUseCase {

    private final InvoiceConverter converter;
    private final InvoiceActiveQueryPort activeQueryPort;
    private final InvoiceNumberQueryPort numberQueryPort;
    private final InvoiceOrderQueryPort orderQueryPort;
    private final InvoiceAmountQueryPort amountQueryPort;
    private final InvoiceCreatedAtQueryPort createdAtQueryPort;
    private final InvoiceCustomerQueryPort customerQueryPort;
    private final InvoiceIssuedByQueryPort issuedByQueryPort;
    private final InvoiceReceivedByQueryPort receivedByQueryPort;
    private final InvoiceUserQueryPort userQueryPort;

    public InvoiceQueryService(InvoiceConverter converter,
                               InvoiceActiveQueryPort activeQueryPort,
                               InvoiceNumberQueryPort numberQueryPort,
                               InvoiceOrderQueryPort orderQueryPort,
                               InvoiceAmountQueryPort amountQueryPort,
                               InvoiceCreatedAtQueryPort createdAtQueryPort,
                               InvoiceCustomerQueryPort customerQueryPort,
                               InvoiceIssuedByQueryPort issuedByQueryPort,
                               InvoiceReceivedByQueryPort receivedByQueryPort,
                               InvoiceUserQueryPort userQueryPort) {
        this.converter = converter;
        this.activeQueryPort = activeQueryPort;
        this.numberQueryPort = numberQueryPort;
        this.orderQueryPort = orderQueryPort;
        this.amountQueryPort = amountQueryPort;
        this.createdAtQueryPort = createdAtQueryPort;
        this.customerQueryPort = customerQueryPort;
        this.issuedByQueryPort = issuedByQueryPort;
        this.receivedByQueryPort = receivedByQueryPort;
        this.userQueryPort = userQueryPort;
    }

    @Override
    public InvoiceDto findDtoByIdAndActive(String id, Boolean active) {
        Invoice inv = activeQueryPort.findByIdAndActive(id, active)
                .orElseThrow(() -> new InvoiceNotFoundException(id));
        return converter.convertToInvoiceDto(inv);
    }

    @Override
    public InvoiceDto findDtoByInvoiceNumberAndActive(String number, Boolean active) {
        Invoice inv = numberQueryPort.findByInvoiceNumberAndActive(number, active)
                .orElseThrow(() -> new InvoiceNumberNotFoundException(number));
        return converter.convertToInvoiceDto(inv);
    }

    @Override
    public InvoiceDto findDtoByOrder_IdAndActive(String orderId, Boolean active) {
        Invoice inv = orderQueryPort.findByOrderIdAndActive(orderId, active)
                .orElseThrow(() -> new InvoiceNotFoundException(orderId));
        return converter.convertToInvoiceDto(inv);
    }

    @Override
    public Set<InvoiceDto> findDtoAllByActiveTrue() {
        return converter.convertToInvoiceSetDto(activeQueryPort.findAllByActiveTrue());
    }

    @Override
    public Set<InvoiceDto> findDtoAllByActiveFalse() {
        return converter.convertToInvoiceSetDto(activeQueryPort.findAllByActiveFalse());
    }

    @Override
    public Set<InvoiceDto> findDtoAllByFinalAmountGreaterThanEqualAndActive(BigDecimal amount, Boolean active) {
        return converter.convertToInvoiceSetDto(
                amountQueryPort.findAllByFinalAmountGreaterThanEqualAndActive(amount, active));
    }

    @Override
    public Set<InvoiceDto> findDtoAllByCreatedAtBetweenAndActive(LocalDateTime start, LocalDateTime end, Boolean active) {
        return converter.convertToInvoiceSetDto(
                createdAtQueryPort.findAllByCreatedAtBetweenAndActive(start, end, active));
    }

    @Override
    public Set<InvoiceDto> findDtoAllByCustomer_IdAndCreatedAtBetweenAndActive(UUID customerId, LocalDateTime start, LocalDateTime end, Boolean active) {
        return converter.convertToInvoiceSetDto(
                customerQueryPort.findAllByCustomerIdAndCreatedAtBetweenAndActive(customerId, start, end, active));
    }

    @Override
    public Set<InvoiceDto> findDtoAllByCustomer_IdAndActiveIs(UUID customerId, Boolean active) {
        return converter.convertToInvoiceSetDto(
                customerQueryPort.findAllByCustomerIdAndActive(customerId, active));
    }

    @Override
    public Set<InvoiceDto> findDtoAllByIssuedByAndActive(String issuedBy, Boolean active) {
        return converter.convertToInvoiceSetDto(
                issuedByQueryPort.findAllByIssuedByAndActive(issuedBy, active));
    }

    @Override
    public Set<InvoiceDto> findDtoAllByReceivedByAndActive(String receivedBy, Boolean active) {
        return converter.convertToInvoiceSetDto(
                receivedByQueryPort.findAllByReceivedByAndActive(receivedBy, active));
    }

    @Override
    public Set<InvoiceDto> findDtoAllByUser_IdAndActive(UUID userId, Boolean active) {
        return converter.convertToInvoiceSetDto(
                userQueryPort.findAllByUserIdAndActive(userId, active));
    }
}
