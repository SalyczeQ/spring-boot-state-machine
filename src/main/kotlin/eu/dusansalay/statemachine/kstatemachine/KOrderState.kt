package eu.dusansalay.statemachine.kstatemachine

import eu.dusansalay.statemachine.common.OrderState
import eu.dusansalay.statemachine.common.OrderState.*
import eu.dusansalay.statemachine.common.PaymentType
import ru.nsk.kstatemachine.DefaultState
import ru.nsk.kstatemachine.FinalState

sealed class KOrderState(
    val orderState: OrderState
) : DefaultState() {

    object NewOrder : KOrderState(NEW_ORDER)
    object WaitingForPayment : KOrderState(WAITING_FOR_PAYMENT)
    object Payed : KOrderState(PAYED)
    object AutomaticallyGeneratedLabels : KOrderState(AUTOMATICALLY_GENERATED_LABELS)
    object WaitingForProcessInWarehouse : KOrderState(WAITING_FOR_PROCESS_IN_WAREHOUSE)
    object OutOfStock : KOrderState(OUT_OF_STOCK)
    object AddressIsNotAuthorized : KOrderState(ADDRESS_IS_NOT_AUTHORIZED)
    object ErrorDuringGeneratingLabels : KOrderState(ERROR_DURING_GENERATING_LABELS)
    object ManuallyBlocked : KOrderState(MANUALLY_BLOCKED)
    object PreparingForShipment : KOrderState(PREPARING_FOR_SHIPMENT)
    object PackedWaitingForPersonalPickup : KOrderState(PACKED_WAITING_FOR_PERSONAL_PICKUP)
    object PackedWaitingForCourier : KOrderState(PACKED_WAITING_FOR_COURIER)
    object PickedUpByCourier : KOrderState(PICKED_UP_BY_COURIER)
    object ReturnedToWarehouseByCourier : KOrderState(RETURNED_TO_WAREHOUSE_BY_COURIER)
    object Closed : KOrderState(CLOSED), FinalState
    object Canceled : KOrderState(CANCELED), FinalState

}