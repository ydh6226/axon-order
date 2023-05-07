package com.fooaxon.order.event.handler

import com.fooaxon.order.entity.Order
import com.fooaxon.order.event.OrderCreatedEvent
import com.fooaxon.order.event.OrderItemInfoChangedEvent
import com.fooaxon.order.repository.OrderRepository
import mu.KotlinLogging
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class OrderEventHandler(
    private val orderRepository: OrderRepository,
) {

    private val logger = KotlinLogging.logger {}

    @EventHandler
    fun on(event: OrderCreatedEvent) {
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

    @EventHandler
    @Transactional
    fun on(event: OrderItemInfoChangedEvent) {
        val order = getOrderById(event)

        order.quantity = event.quantity
        order.price = event.price
    }

    private fun getOrderById(event: OrderItemInfoChangedEvent) = (orderRepository.findByOrderId(event.orderId)
        ?: throw IllegalArgumentException("${event.orderId} 주문을 찾을 수 없습니다."))
}
