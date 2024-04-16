package eu.dusansalay.statemachine.service

import eu.dusansalay.statemachine.common.OrderEvent
import eu.dusansalay.statemachine.common.OrderState
import eu.dusansalay.statemachine.kstatemachine.KOrderEvent.Companion.fromOrderEvent
import eu.dusansalay.statemachine.kstatemachine.KOrderStateMachineBuilder
import eu.dusansalay.statemachine.repository.OrderEntity
import eu.dusansalay.statemachine.repository.OrderRepository
import eu.dusansalay.statemachine.spring.statemanager.OrderStateMachineBuilder
import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.reactor.mono
import org.springframework.messaging.support.MessageBuilder
import org.springframework.statemachine.StateMachine
import org.springframework.statemachine.config.StateMachineFactory
import org.springframework.stereotype.Service
import java.util.*

@Service
class OrderService(
    private val orderStateMachineBuilder: OrderStateMachineBuilder,
    private val kOrderStateMachineBuilder: KOrderStateMachineBuilder,
    private val orderRepository: OrderRepository,
)
{
    suspend fun changeOrderState(orderId: UUID, orderEvent: OrderEvent)
    {
        val message = MessageBuilder
            .withPayload(orderEvent)
            .setHeader("orderId", orderId)
            .setHeader("order", getOrder(orderId))
            .build()
        val stateMachine = orderStateMachineBuilder.build(orderId)
        stateMachine.sendEvent(mono { message }).awaitSingle()
    }

    suspend fun kChangeOrderState(orderId: UUID, orderEvent: OrderEvent)
    {
        val stateMachine = kOrderStateMachineBuilder.build(orderId)

        val orderEntity =  orderRepository.findById(orderId) ?: throw RuntimeException("Order with id $orderId not found.")
        stateMachine.processEvent(fromOrderEvent(orderEvent, orderEntity))
    }

    suspend fun getOrder(orderId: UUID): OrderEntity?
    {
       return orderRepository.findById(orderId)
    }
}