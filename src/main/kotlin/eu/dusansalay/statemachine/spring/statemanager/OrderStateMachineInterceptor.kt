package eu.dusansalay.statemachine.spring.statemanager

import eu.dusansalay.statemachine.common.OrderEvent
import eu.dusansalay.statemachine.common.OrderState
import eu.dusansalay.statemachine.repository.OrderRepository
import kotlinx.coroutines.runBlocking
import org.springframework.messaging.Message
import org.springframework.statemachine.StateMachine
import org.springframework.statemachine.state.State
import org.springframework.statemachine.support.StateMachineInterceptorAdapter
import org.springframework.statemachine.transition.Transition
import org.springframework.stereotype.Component
import java.util.*

@Component
class OrderStateMachineInterceptor(
    private val orderRepository: OrderRepository
) : StateMachineInterceptorAdapter<OrderState, OrderEvent>() {

    override fun preStateChange(
        state: State<OrderState, OrderEvent>?,
        message: Message<OrderEvent>?,
        transition: Transition<OrderState, OrderEvent>?,
        stateMachine: StateMachine<OrderState, OrderEvent>?,
        rootStateMachine: StateMachine<OrderState, OrderEvent>?
    ): Unit = runBlocking {
        val orderId = message?.headers?.get("orderId") as UUID
        val order = orderRepository.findById(orderId) ?: throw RuntimeException("Order with id $orderId not found.")
        orderRepository.save(order.copy(state = state?.id ?: throw RuntimeException("State is null.")))
    }
}