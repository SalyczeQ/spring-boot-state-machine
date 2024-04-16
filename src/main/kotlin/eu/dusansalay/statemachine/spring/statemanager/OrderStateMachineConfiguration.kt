package eu.dusansalay.statemachine.spring.statemanager

import eu.dusansalay.statemachine.common.OrderEvent
import eu.dusansalay.statemachine.common.OrderState
import eu.dusansalay.statemachine.spring.statemanager.action.GeneratingCourierLabelsAction
import eu.dusansalay.statemachine.spring.statemanager.guard.OnlinePaymentGuard
import org.springframework.context.annotation.Configuration
import org.springframework.statemachine.config.EnableStateMachineFactory
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer
import java.util.*


@Configuration
@EnableStateMachineFactory
class OrderStateMachineConfiguration(
    private val onlinePaymentGuard: OnlinePaymentGuard,
    private val generatingCourierLabelsAction: GeneratingCourierLabelsAction,
    private val orderStateErrorListener: OrderStateErrorListener,
    private val orderStateListener: OrderStateListener,
    private val orderRuntimePersister: OrderRuntimePersister
) : EnumStateMachineConfigurerAdapter<OrderState, OrderEvent>() {

    override fun configure(config: StateMachineConfigurationConfigurer<OrderState, OrderEvent>) {
        config
            .withPersistence()
            .runtimePersister(orderRuntimePersister)
            .and()
            .withConfiguration()
            .listener(orderStateErrorListener)
            .listener(orderStateListener)
            .autoStartup(false)
    }

    override fun configure(states: StateMachineStateConfigurer<OrderState, OrderEvent>) {
        states
            .withStates()
            .initial(OrderState.NEW_ORDER)
            .end(OrderState.CLOSED)
            .end(OrderState.CANCELED)
            .states(EnumSet.allOf(OrderState::class.java))
    }

    override fun configure(transitions: StateMachineTransitionConfigurer<OrderState, OrderEvent>) {
        transitions
            .withExternal()
            .source(OrderState.NEW_ORDER)
            .target(OrderState.WAITING_FOR_PAYMENT)
            .event(OrderEvent.ORDER_CREATED_WITH_ONLINE_PAYMENT).guard(onlinePaymentGuard)
            .and()
            .withExternal()
            .source(OrderState.NEW_ORDER)
            .target(OrderState.OUT_OF_STOCK)
            .event(OrderEvent.ORDER_CREATED_WITH_OFFLINE_PAYMENT_WITH_SOME_PRODUCTS_OUT_OF_STOCK)
            .and()
            .withExternal()
            .source(OrderState.NEW_ORDER)
            .target(OrderState.AUTOMATICALLY_GENERATED_LABELS)
            .event(OrderEvent.ORDER_CREATED_WITH_OFFLINE_PAYMENT_WITH_ALL_PRODUCTS_IN_STOCK).action(generatingCourierLabelsAction)
            .and()
            .withExternal()
            .source(OrderState.NEW_ORDER)
            .target(OrderState.MANUALLY_BLOCKED)
            .event(OrderEvent.MANUALLY_BLOCK_ORDER)
            .secured("ROLE_BO_ADMIN") // custom security ideally for back office users and manual transitions
            .and()
    }


}