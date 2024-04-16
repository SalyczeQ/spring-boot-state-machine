package eu.dusansalay.statemachine.spring.statemanager

import eu.dusansalay.statemachine.common.OrderEvent
import eu.dusansalay.statemachine.common.OrderState
import org.springframework.statemachine.StateMachine
import org.springframework.statemachine.listener.StateMachineListenerAdapter
import org.springframework.stereotype.Component
import java.lang.Exception

@Component
class OrderStateErrorListener : StateMachineListenerAdapter<OrderState, OrderEvent>()
{
    override fun stateMachineError(stateMachine: StateMachine<OrderState, OrderEvent>, exception: Exception) {
        //TODO handling exceptions
        when (exception) {
            is IllegalArgumentException -> {
                println("IllegalArgumentException")
            }
            is IllegalStateException -> {
                println("IllegalStateException")
            }
            else -> {
                println("Unknown exception")
            }
        }
    }
}