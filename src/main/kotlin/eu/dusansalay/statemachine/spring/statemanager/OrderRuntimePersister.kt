package eu.dusansalay.statemachine.spring.statemanager

import eu.dusansalay.statemachine.common.OrderEvent
import eu.dusansalay.statemachine.common.OrderState
import eu.dusansalay.statemachine.repository.OrderEntity
import eu.dusansalay.statemachine.repository.OrderRepository
import org.slf4j.LoggerFactory
import org.springframework.statemachine.StateMachineContext
import org.springframework.statemachine.persist.StateMachineRuntimePersister
import org.springframework.statemachine.support.DefaultStateMachineContext
import org.springframework.statemachine.support.StateMachineInterceptor
import org.springframework.stereotype.Component

@Component
class OrderRuntimePersister(
    private val orderRepository: OrderRepository,
    private val orderStateMachineInterceptor: OrderStateMachineInterceptor,
) :
    StateMachineRuntimePersister<OrderState, OrderEvent, OrderEntity> {
    override fun write(context: StateMachineContext<OrderState, OrderEvent>?, contextObj: OrderEntity?) {
        log.info("Writing contextObj: $contextObj")
    }

    override fun read(contextObj: OrderEntity?): StateMachineContext<OrderState, OrderEvent> {
        val eventHeaders = mapOf("orderId" to contextObj?.id)
        return DefaultStateMachineContext(contextObj?.state, null, eventHeaders, null)
    }

    override fun getInterceptor(): StateMachineInterceptor<OrderState, OrderEvent> {
         return orderStateMachineInterceptor
    }

    companion object {
        private val log = LoggerFactory.getLogger(OrderRuntimePersister::class.java)
    }
}