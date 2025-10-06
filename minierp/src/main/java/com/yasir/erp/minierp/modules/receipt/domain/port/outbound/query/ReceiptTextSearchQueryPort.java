package com.yasir.erp.minierp.modules.receipt.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.receipt.domain.model.Receipt;

import java.time.LocalDateTime;
import java.util.Set;

public interface ReceiptTextSearchQueryPort {
    Set<Receipt> findAllByIssuedByAndActive(String issuedBy, boolean active);
    Set<Receipt> findAllByReceivedByAndActive(String receivedBy, boolean active);
    Set<Receipt> findAllByIssuedByAndDateBetweenAndActive(String issuedBy, LocalDateTime start, LocalDateTime end, boolean active);
}