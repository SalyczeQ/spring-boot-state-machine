package eu.dusansalay.statemachine.common


enum class OrderState {
    //v prirave
    NEW_ORDER,
    WAITING_FOR_PAYMENT,
    PAYED,
    AUTOMATICALLY_GENERATED_LABELS,
    WAITING_FOR_PROCESS_IN_WAREHOUSE,
    //blokace
    OUT_OF_STOCK,
    ADDRESS_IS_NOT_AUTHORIZED,
    ERROR_DURING_GENERATING_LABELS,
    MANUALLY_BLOCKED, //z kazdeho stavu??
    //v expedici
    PREPARING_FOR_SHIPMENT,
    PACKED_WAITING_FOR_PERSONAL_PICKUP,
    PACKED_WAITING_FOR_COURIER,
    PICKED_UP_BY_COURIER,
    RETURNED_TO_WAREHOUSE_BY_COURIER,
    //dokonceno
    CLOSED,
    CANCELED,
}