package com.yasir.erp.minierp.modules.order.domain.model

enum class OrderStatus {
    PENDING,         // BEKLEYEN
    PROCESSING,      // İŞLENİYOR
    DISPATCHED,      // SEVK EDİLDİ
    DELIVERED,       // TESLİM EDİLDİ
    CANCELLED        // İPTAL EDİLDİ
}