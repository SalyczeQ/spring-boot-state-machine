package eu.dusansalay.statemachine.spring.statemanager

import eu.dusansalay.statemachine.common.OrderEvent
import eu.dusansalay.statemachine.common.OrderState
import eu.dusansalay.statemachine.repository.OrderRepository
import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.statemachine.StateMachine
import org.springframework.statemachine.config.StateMachineFactory
import org.springframework.statemachine.support.DefaultStateMachineContext
import org.springframework.stereotype.Component
import java.util.*

@Component
class OrderStateMachineBuilder(
    private val stateMachineFactory: StateMachineFactory<OrderState, OrderEvent>,
    private val orderRepository: OrderRepository,
    private val orderStateMachineInterceptor: OrderStateMachineInterceptor
) {

    suspend fun build(orderId: UUID): StateMachine<OrderState, OrderEvent> {
        val order = orderRepository.findById(orderId) ?: throw RuntimeException("Order with id $orderId not found.")
        val stateMachine = stateMachineFactory.getStateMachine(orderId)
        // this stop is not necessary, because auto start is false
        stateMachine.stopReactively().awaitSingleOrNull()
        stateMachine.stateMachineAccessor.doWithAllRegions { sma ->
            sma.addStateMachineInterceptor(orderStateMachineInterceptor)
            sma.resetStateMachineReactively(DefaultStateMachineContext(order.state, null, null, null)).subscribe()
        }
        stateMachine.startReactively().awaitSingleOrNull()
        return stateMachine
    }
}