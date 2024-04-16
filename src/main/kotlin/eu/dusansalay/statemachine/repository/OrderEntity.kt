package eu.dusansalay.statemachine.repository

import eu.dusansalay.statemachine.common.OrderState
import eu.dusansalay.statemachine.common.PaymentType
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table("orders")
data class OrderEntity(
    @Id
    val id: UUID,
    val state: OrderState,
    val paymentType: PaymentType,
)