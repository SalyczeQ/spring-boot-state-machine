package eu.dusansalay.statemachine.controller

import eu.dusansalay.statemachine.common.OrderEvent
import eu.dusansalay.statemachine.repository.OrderEntity
import eu.dusansalay.statemachine.service.OrderService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class OrderController(
    private val orderService: OrderService
) {

    @GetMapping("/change-order-state")
    suspend fun changeStateOrder(@RequestParam orderId: UUID, @RequestParam orderEvent: OrderEvent): String {
        val orderBeforeChange = orderService.getOrder(orderId)
        orderService.changeOrderState(orderId, orderEvent)
        val orderAfterChange = orderService.getOrder(orderId)
        return "Order state changed from ${orderBeforeChange?.state} to ${orderAfterChange?.state}"
    }

    @GetMapping("/k-change-order-state")
    suspend fun kChangeStateOrder(@RequestParam orderId: UUID, @RequestParam orderEvent: OrderEvent): String {
        val orderBeforeChange = orderService.getOrder(orderId)
        orderService.kChangeOrderState(orderId, orderEvent)
        val orderAfterChange = orderService.getOrder(orderId)
        return "Order state changed from ${orderBeforeChange?.state} to ${orderAfterChange?.state}"
    }

    @GetMapping("/order")
    suspend fun getOrder(@RequestParam orderId: UUID): OrderEntity? {
        return orderService.getOrder(orderId)
    }

}