package com.zalmanhack.tireshop.domains.enums;

public enum OrderStatus {
    ACTIVE,         // заказ назначен
    IN_PROCESSING,  // заказ в обработке
    IN_PROGRESS,    // заказ выполняется
    CANCELED,       // заказ отменен
    COMPLETED,      // заказ завершен и ожидает
    CLOSED          // заказ завершен и закрыт
}
