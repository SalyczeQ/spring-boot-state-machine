package eu.dusansalay.statemachine.spring.statemanager.guard

import eu.dusansalay.statemachine.common.OrderEvent
import eu.dusansalay.statemachine.common.OrderState
import eu.dusansalay.statemachine.common.PaymentType
import eu.dusansalay.statemachine.repository.OrderEntity
import eu.dusansalay.statemachine.repository.OrderRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.reactor.mono
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import org.springframework.statemachine.StateContext
import org.springframework.statemachine.guard.Guard
import org.springframework.stereotype.Component
import java.util.*

@Component
class OnlinePaymentGuard : Guard<OrderState, OrderEvent> {

    override fun evaluate(context: StateContext<OrderState, OrderEvent>): Boolean {
            val orderEntity = context.messageHeaders["order"] as OrderEntity
            //for example: check if target is correct
            context.target.id
            return orderEntity.paymentType == PaymentType.ONLINE
    }
}