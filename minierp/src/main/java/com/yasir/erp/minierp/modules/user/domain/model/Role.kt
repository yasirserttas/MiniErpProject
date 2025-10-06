package com.yasir.erp.minierp.modules.user.domain.model



enum class Role  {

    USER, // Sisteme giriş yapabilen en basic kullanıcı

    ADMIN, // Tüm modülleri görebilen ve yönetebilen sistem yöneticisi

    FINANCE, // Payment, invoice, Cheque, PromissoryNote, BankMovement ekranlarına erişim

    WAREHOUSE, // Stock ve StockMovement işlemlerini yönetir

    SALES, // Order, OrderItem, Customer ve DispatchNote işlemlerini yapar

    PURCHASE, // PurchaseOrder, PurchaseItem ve Supplier işlemlerini yapar

    RECEIPT_EDITOR, // Receipt (tahsilat makbuzu) düzenleyebilir

    PRODUCT_MANAGER, // Product CRUD yetkisi olan, fiyat ve KDV tanımlarını yapabilen kişi

    REPORT_VIEWER, // Sadece raporlara ve listelere erişebilir, işlem yapamaz;
    ;


}