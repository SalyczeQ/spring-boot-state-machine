package eu.dusansalay.statemachine.kstatemachine

import eu.dusansalay.statemachine.common.OrderState.*
import eu.dusansalay.statemachine.kstatemachine.KOrderEvent.OrderCreatedWithOnlinePayment
import eu.dusansalay.statemachine.kstatemachine.KOrderState.*
import eu.dusansalay.statemachine.repository.OrderRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.newSingleThreadContext
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import ru.nsk.kstatemachine.*
import java.util.*

@Component
class KOrderStateMachineBuilder(
    private val orderRepository: OrderRepository
) {

    suspend fun build(orderId: UUID): StateMachine {
        val orderEntity =
            orderRepository.findById(orderId) ?: throw RuntimeException("Order with id $orderId not found.")
        val machine = createStateMachine(
            scope = CoroutineScope(newSingleThreadContext("single thread")),
            start = false,
        ) {

            logger = StateMachine.Logger { message -> LOG.info(message()) }
            listenerExceptionHandler =
                StateMachine.ListenerExceptionHandler { exception -> LOG.error("Exception in listener", exception) }

            addInitialState(NewOrder) {
                transition<OrderCreatedWithOnlinePayment> {
                    targetState = WaitingForPayment
//                    guard = {
//                        val order = this@addInitialState.payload as OrderEntity
//                        order.paymentType == PaymentType.ONLINE
//                    }
                    onTriggered {
                        println(it.event)
                        println("Switching to $targetState, with argument: ${it.argument}")
                    }
                }
            }
            addFinalState(Closed)
            addFinalState(Canceled)


            onTransitionComplete { transitionParams, activeStates ->
                if (activeStates.size > 1) throw IllegalStateException("More than one active state: $activeStates")
                val state = activeStates.first() as KOrderState
                orderRepository.save(orderEntity.copy(state = state.orderState))
            }
        }


        when (orderEntity.state) {
            NEW_ORDER -> machine.start(NewOrder)
            WAITING_FOR_PAYMENT -> machine.start(WaitingForPayment)
            PAYED -> machine.start(Payed)
            AUTOMATICALLY_GENERATED_LABELS -> machine.start(AutomaticallyGeneratedLabels)
            WAITING_FOR_PROCESS_IN_WAREHOUSE -> machine.start(WaitingForProcessInWarehouse)
            OUT_OF_STOCK -> machine.start(OutOfStock)
            ADDRESS_IS_NOT_AUTHORIZED -> machine.start(AddressIsNotAuthorized)
            ERROR_DURING_GENERATING_LABELS -> machine.start(ErrorDuringGeneratingLabels)
            MANUALLY_BLOCKED -> machine.start(ManuallyBlocked)
            PREPARING_FOR_SHIPMENT -> machine.start(PreparingForShipment)
            PACKED_WAITING_FOR_PERSONAL_PICKUP -> machine.start(PackedWaitingForPersonalPickup)
            PACKED_WAITING_FOR_COURIER -> machine.start(PackedWaitingForCourier)
            PICKED_UP_BY_COURIER -> machine.start(PickedUpByCourier)
            RETURNED_TO_WAREHOUSE_BY_COURIER -> machine.start(ReturnedToWarehouseByCourier)
            CLOSED -> machine.start(Closed)
            CANCELED -> machine.start(Canceled)
        }
        LOG.info("State machine for order $orderId created. With state: ${orderEntity.state}")
        return machine
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(KOrderStateMachineBuilder::class.java)
    }
}