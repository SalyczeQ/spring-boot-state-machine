package eu.dusansalay.statemachine.repository

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface OrderRepository : CoroutineCrudRepository<OrderEntity, UUID>{

}