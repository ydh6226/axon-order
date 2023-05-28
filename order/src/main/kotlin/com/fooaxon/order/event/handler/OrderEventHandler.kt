package com.fooaxon.order.event.handler

import com.fooaxon.order.dto.OrderQuery
import com.fooaxon.order.entity.Order
import com.fooaxon.order.event.OrderCreatedEvent
import com.fooaxon.order.event.OrderItemInfoChangedEvent
import com.fooaxon.order.event.OrderShippingPreparedEvent
import com.fooaxon.order.repository.OrderRepository
import mu.KotlinLogging
import org.axonframework.config.ProcessingGroup
import org.axonframework.eventhandling.AllowReplay
import org.axonframework.eventhandling.EventHandler
import org.axonframework.eventhandling.ResetHandler
import org.axonframework.queryhandling.QueryHandler
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

        val order = getOrderById(event.orderId)

        order.quantity = event.quantity
        order.price = event.price
    }

    @EventHandler
    @AllowReplay
    @Transactional
    fun on(event: OrderShippingPreparedEvent) {
        logger.info { "[order projection :: OrderShippingPreparedEvent] 주문배송준비 완료: ${event}" }

        val order = getOrderById(event.orderId)

        order.status = event.orderStatus
    }

    @QueryHandler
    fun on(query: OrderQuery): Order {
        logger.info { "[order projection:: OrderQuery] 주문조회: ${query}" }
        return getOrderById(query.orderId)
    }

    @ResetHandler
    fun resetOrders() {
        logger.info { "[order projection :: reset]" }
        orderRepository.deleteAll()
    }

    private fun getOrderById(orderId: String): Order {
        return (orderRepository.findByOrderId(orderId)
            ?: throw IllegalArgumentException("$orderId 주문을 찾을 수 없습니다."))
    }
}
