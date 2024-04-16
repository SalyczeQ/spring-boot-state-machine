package eu.dusansalay.statemachine.spring.statemanager

import eu.dusansalay.statemachine.common.OrderEvent
import eu.dusansalay.statemachine.common.OrderState
import org.springframework.statemachine.StateMachine
import org.springframework.statemachine.listener.StateMachineListenerAdapter
import org.springframework.statemachine.state.State
import org.springframework.stereotype.Component
import java.lang.Exception

@Component
class OrderStateListener : StateMachineListenerAdapter<OrderState, OrderEvent>()
{
    override fun stateChanged(from: State<OrderState, OrderEvent>?, to: State<OrderState, OrderEvent>?) {
        log.info("State changed from: ${from?.id} to: ${to?.id}")
    }

    companion object {
        private val log = org.slf4j.LoggerFactory.getLogger(OrderStateListener::class.java)
    }
}