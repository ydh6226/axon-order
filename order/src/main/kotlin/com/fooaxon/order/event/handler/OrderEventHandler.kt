package com.fooaxon.order.event.handler

import com.fooaxon.order.entity.Order
import com.fooaxon.order.event.OrderCreatedEvent
import com.fooaxon.order.event.OrderItemInfoChangedEvent
import com.fooaxon.order.repository.OrderRepository
import mu.KotlinLogging
import org.axonframework.config.ProcessingGroup
import org.axonframework.eventhandling.AllowReplay
import org.axonframework.eventhandling.EventHandler
import org.axonframework.eventhandling.ResetHandler
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

/**
 * Order Projection
 */
@Component
@ProcessingGroup("orders")
class OrderEventHandler(
    private val orderRepository: OrderRepository,
) {

    private val logger = KotlinLogging.logger {}

    @EventHandler
    @AllowReplay
    @Transactional
    fun on(event: OrderCreatedEvent) {
        // TODO: 사가로 이동
        logger.info { "[order projection :: OrderCreatedEvent] 주문 DB 저장: ${event}" }

        val order = Order(
            orderId = event.orderId,
            itemName = event.itemName,
            quantity = event.quantity,
            price = event.price
        )

        orderRepository.save(order)
    }

    @EventHandler
    @AllowReplay
    @Transactional
    fun on(event: OrderItemInfoChangedEvent) {
        logger.info { "[order projection :: OrderItemInfoChangedEvent] 주문상품 정보 변경: ${event}" }

        val order = getOrderById(event)

        order.quantity = event.quantity
        order.price = event.price
    }

    @ResetHandler
    fun resetOrders() {
        logger.info { "[order projection :: reset]" }
        orderRepository.deleteAll()
    }

    private fun getOrderById(event: OrderItemInfoChangedEvent) = (orderRepository.findByOrderId(event.orderId)
        ?: throw IllegalArgumentException("${event.orderId} 주문을 찾을 수 없습니다."))
}
