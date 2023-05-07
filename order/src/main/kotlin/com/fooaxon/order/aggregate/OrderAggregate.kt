package com.fooaxon.order.aggregate

import com.fooaxon.order.command.ChangeOrderItemInfoCommand
import com.fooaxon.order.command.CreateOrderCommand
import com.fooaxon.order.entity.OrderStatus
import com.fooaxon.order.event.OrderCreatedEvent
import com.fooaxon.order.event.OrderItemInfoChangedEvent
import mu.KotlinLogging
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate
import java.math.BigDecimal

// protoType scope인 @Component
@Aggregate
class OrderAggregate {
    @AggregateIdentifier
    lateinit var orderId: String
    lateinit var status: OrderStatus
    lateinit var itemName: String
    var quantity: Int = 0
    lateinit var price: BigDecimal

    private val logger = KotlinLogging.logger {}

    constructor() {
        logger.info { "기본생성자 호출" }
    }


    @CommandHandler
    constructor(command: CreateOrderCommand) {
        logger.info { "주문생성 커맨드: orderId: ${command.orderId}, command: ${command}" }
        require(command.quantity > 0) { "주문수량은 0보다 커야합니다. ${command.orderId}, ${command.quantity}" }
        require(command.price > BigDecimal.ZERO) { "주문가격은 0보다 커야합니다. ${command.orderId}, ${command.price}" }

        val event = command.toEvent(command.orderId, OrderStatus.CREATED)

        logger.info { "주문생성 이벤트 발행: ${event}" }
        AggregateLifecycle.apply(event)
    }

    @EventSourcingHandler
    fun on(event: OrderCreatedEvent) {
        logger.info { "주문 aggregate 생성 수량: ${event.quantity}, 가격: ${event.price}" }
        this.orderId = event.orderId
        this.status = event.orderStatus
        this.itemName = event.itemName
        this.price = event.price
        this.quantity = event.quantity
    }

    // 이벤트를 replay하기 위해 명시적으로 호출 가능한 기본생성자가 있어야함.
    @CommandHandler
    fun on(command: ChangeOrderItemInfoCommand) {
        require(command.quantity > 0) { "주문수량은 0보다 커야합니다. ${command.orderId}, ${command.quantity}" }
        require(command.price > BigDecimal.ZERO) { "주문가격은 0보다 커야합니다. ${command.orderId}, ${command.price}" }

        AggregateLifecycle.apply(command.toEvent())
    }

    @EventSourcingHandler
    fun on(event: OrderItemInfoChangedEvent) {
        logger.info { "주문 aggregate 상품 정보 변경 수량: ${event.quantity}, 가격: ${event.price}" }
        this.price = event.price
        this.quantity = event.quantity
    }
}
