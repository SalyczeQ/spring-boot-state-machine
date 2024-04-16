package eu.dusansalay.statemachine.kstatemachine

import eu.dusansalay.statemachine.common.OrderEvent
import eu.dusansalay.statemachine.repository.OrderEntity
import ru.nsk.kstatemachine.DataEvent

sealed class KOrderEvent(
    val orderEvent: OrderEvent
): DataEvent<OrderEntity> {
    class OrderCreatedWithOnlinePayment(override val data: OrderEntity) : KOrderEvent(OrderEvent.ORDER_CREATED_WITH_ONLINE_PAYMENT)
    class OrderCreatedWithOfflinePaymentWithAllProductsInStock(override val data: OrderEntity) : KOrderEvent(OrderEvent.ORDER_CREATED_WITH_OFFLINE_PAYMENT_WITH_ALL_PRODUCTS_IN_STOCK)
    class OrderCreatedWithOfflinePaymentWithSomeProductsOutOfStock(override val data: OrderEntity) : KOrderEvent(OrderEvent.ORDER_CREATED_WITH_OFFLINE_PAYMENT_WITH_SOME_PRODUCTS_OUT_OF_STOCK)
    class UnblockOrderWithSomeProductsOutOfStock(override val data: OrderEntity) : KOrderEvent(OrderEvent.UNBLOCK_ORDER_WITH_SOME_PRODUCTS_OUT_OF_STOCK)
    class OrderPayedWithOnlinePayment(override val data: OrderEntity) : KOrderEvent(OrderEvent.ORDER_PAYED_WITH_ONLINE_PAYMENT)
    class BlockPayedOrderWithSomeProductsOutOfStock(override val data: OrderEntity) : KOrderEvent(OrderEvent.BLOCK_PAYED_ORDER_WITH_SOME_PRODUCTS_OUT_OF_STOCK)
    class GenerateLabels(override val data: OrderEntity) : KOrderEvent(OrderEvent.GENERATE_LABELS)
    class BlockOrderWithFailedLabelGeneration(override val data: OrderEntity) : KOrderEvent(OrderEvent.BLOCK_ORDER_WITH_FAILED_LABEL_GENERATION)
    class BlockOrderWithAddressNotAuthorized(override val data: OrderEntity) : KOrderEvent(OrderEvent.BLOCK_ORDER_WITH_ADDRESS_NOT_AUTHORIZED)
    class UnblockOrderWithGeneratedLabels(override val data: OrderEntity) : KOrderEvent(OrderEvent.UNBLOCK_ORDER_WITH_GENERATED_LABELS)
    class UnblockOrderWithoutGeneratedLabels(override val data: OrderEntity) : KOrderEvent(OrderEvent.UNBLOCK_ORDER_WITHOUT_GENERATED_LABELS)
    class OrderAutomaticallyClosedAfterOnlinePayment(override val data: OrderEntity) : KOrderEvent(OrderEvent.ORDER_AUTOMATICALLY_CLOSED_AFTER_ONLINE_PAYMENT)
    class OrderReadyForProcessInWarehouse(override val data: OrderEntity) : KOrderEvent(OrderEvent.ORDER_READY_FOR_PROCESS_IN_WAREHOUSE)
    class OrderAcceptedByWarehouse(override val data: OrderEntity) : KOrderEvent(OrderEvent.ORDER_ACCEPTED_BY_WAREHOUSE)
    class OrderDroppedByWarehouse(override val data: OrderEntity) : KOrderEvent(OrderEvent.ORDER_DROPPED_BY_WAREHOUSE)
    class ManuallyBlockOrder(override val data: OrderEntity) : KOrderEvent(OrderEvent.MANUALLY_BLOCK_ORDER)

    companion object {
        fun fromOrderEvent(orderEvent: OrderEvent, orderEntity: OrderEntity): KOrderEvent = when(orderEvent) {
            OrderEvent.ORDER_CREATED_WITH_ONLINE_PAYMENT -> OrderCreatedWithOnlinePayment(orderEntity)
            OrderEvent.ORDER_CREATED_WITH_OFFLINE_PAYMENT_WITH_ALL_PRODUCTS_IN_STOCK -> OrderCreatedWithOfflinePaymentWithAllProductsInStock(orderEntity)
            OrderEvent.ORDER_CREATED_WITH_OFFLINE_PAYMENT_WITH_SOME_PRODUCTS_OUT_OF_STOCK -> OrderCreatedWithOfflinePaymentWithSomeProductsOutOfStock(orderEntity)
            OrderEvent.UNBLOCK_ORDER_WITH_SOME_PRODUCTS_OUT_OF_STOCK -> UnblockOrderWithSomeProductsOutOfStock(orderEntity)
            OrderEvent.ORDER_PAYED_WITH_ONLINE_PAYMENT -> OrderPayedWithOnlinePayment(orderEntity)
            OrderEvent.BLOCK_PAYED_ORDER_WITH_SOME_PRODUCTS_OUT_OF_STOCK -> BlockPayedOrderWithSomeProductsOutOfStock(orderEntity)
            OrderEvent.GENERATE_LABELS -> GenerateLabels(orderEntity)
            OrderEvent.BLOCK_ORDER_WITH_FAILED_LABEL_GENERATION -> BlockOrderWithFailedLabelGeneration(orderEntity)
            OrderEvent.BLOCK_ORDER_WITH_ADDRESS_NOT_AUTHORIZED -> BlockOrderWithAddressNotAuthorized(orderEntity)
            OrderEvent.UNBLOCK_ORDER_WITH_GENERATED_LABELS -> UnblockOrderWithGeneratedLabels(orderEntity)
            OrderEvent.UNBLOCK_ORDER_WITHOUT_GENERATED_LABELS -> UnblockOrderWithoutGeneratedLabels(orderEntity)
            OrderEvent.ORDER_AUTOMATICALLY_CLOSED_AFTER_ONLINE_PAYMENT -> OrderAutomaticallyClosedAfterOnlinePayment(orderEntity)
            OrderEvent.ORDER_READY_FOR_PROCESS_IN_WAREHOUSE -> OrderReadyForProcessInWarehouse(orderEntity)
            OrderEvent.ORDER_ACCEPTED_BY_WAREHOUSE -> OrderAcceptedByWarehouse(orderEntity)
            OrderEvent.ORDER_DROPPED_BY_WAREHOUSE -> OrderDroppedByWarehouse(orderEntity)
            OrderEvent.MANUALLY_BLOCK_ORDER -> ManuallyBlockOrder(orderEntity)
        }
    }
}