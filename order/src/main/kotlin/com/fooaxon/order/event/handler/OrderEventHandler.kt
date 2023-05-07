package com.fooaxon.order.event.handler

import com.fooaxon.order.entity.Order
import com.fooaxon.order.event.OrderCreatedEvent
import com.fooaxon.order.repository.OrderRepository
import mu.KotlinLogging
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component

@Component
class OrderEventHandler(
    private val orderRepository: OrderRepository,
) {

    private val logger = KotlinLogging.logger {}

    @EventHandler
    fun saveOrder(event: OrderCreatedEvent) {
        // TODO: 사가로 이동
        logger.info { "주문 DB 저장: ${event}" }

        val order = Order(
            orderId = event.orderId,
            itemName = event.itemName,
            quantity = event.quantity,
            price = event.price
        )

        orderRepository.save(order)
    }
}
