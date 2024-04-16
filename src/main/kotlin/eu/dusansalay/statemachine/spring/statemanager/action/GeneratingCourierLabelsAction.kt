package eu.dusansalay.statemachine.spring.statemanager.action

import eu.dusansalay.statemachine.common.OrderEvent
import eu.dusansalay.statemachine.common.OrderState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.springframework.statemachine.StateContext
import org.springframework.statemachine.action.Action
import org.springframework.stereotype.Component

@Component
class GeneratingCourierLabelsAction(
    private val coroutineScope: CoroutineScope
) : Action<OrderState, OrderEvent> {

    override fun execute(context: StateContext<OrderState, OrderEvent>)
    = runBlocking(Dispatchers.Default) {
        TODO("RUN GENERATING COURIER LABELS")
    }

    //or detached coroutine
//    override fun execute(context: StateContext<OrderState, OrderEvent>)
//            = coroutineScope.launch {
//        TODO("RUN GENERATING COURIER LABELS")
//    }
}