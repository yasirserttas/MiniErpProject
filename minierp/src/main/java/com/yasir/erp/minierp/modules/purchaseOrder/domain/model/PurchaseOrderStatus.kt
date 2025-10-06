package com.yasir.erp.minierp.modules.purchaseOrder.domain.model

enum class PurchaseOrderStatus {
    PENDING,       // Sipariş oluşturuldu ama onaylanmadı
    APPROVED,      // Yönetici veya satın almacı onayladı
    ORDERED,       // Tedarikçiye sipariş iletildi
    RECEIVED,      // Mallar teslim alındı
    CANCELLED      // Sipariş iptal edildi
}