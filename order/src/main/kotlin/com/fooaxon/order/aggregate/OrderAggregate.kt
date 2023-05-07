package com.fooaxon.order.aggregate

import com.fooaxon.order.command.CreateOrderCommand
import com.fooaxon.order.entity.OrderStatus
import com.fooaxon.order.event.OrderCreatedEvent
import mu.KotlinLogging
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate
import java.math.BigDecimal

@Aggregate
class OrderAggregate {
    @AggregateIdentifier
    lateinit var orderId: String
    lateinit var status: OrderStatus
    lateinit var itemName: String
    var quantity: Int = 0
    lateinit var price: BigDecimal

    private val logger = KotlinLogging.logger {}

    @CommandHandler
    constructor(command: CreateOrderCommand) {
        logger.info { "주문생성 커맨드: orderId: ${command.orderId}, command: ${command}" }
        val event = command.toEvent(command.orderId, OrderStatus.CREATED)

        logger.info { "주문생성 이벤트 발행: ${event}" }
        AggregateLifecycle.apply(event)
    }

    @EventSourcingHandler
    fun on(event: OrderCreatedEvent) {
        logger.info { "주문 aggregate 생성" }
        this.orderId = event.orderId
        this.status = event.orderStatus
        this.itemName = event.itemName
        this.price = event.price
        this.quantity = event.quantity
    }

}
