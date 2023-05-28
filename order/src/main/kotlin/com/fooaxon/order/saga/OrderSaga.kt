package com.fooaxon.order.saga

import com.common.command.DecreaseStockQuantityCommand
import com.common.event.DecreaseStockQuantityApprovedEvent
import com.common.event.DecreaseStockQuantityRejectedEvent
import com.fooaxon.order.command.PrepareShippingCommand
import com.fooaxon.order.event.OrderCreatedEvent
import com.fooaxon.order.event.OrderShippingPreparedEvent
import mu.KotlinLogging
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.modelling.saga.EndSaga
import org.axonframework.modelling.saga.SagaEventHandler
import org.axonframework.modelling.saga.SagaLifecycle
import org.axonframework.modelling.saga.StartSaga
import org.axonframework.spring.stereotype.Saga
import org.springframework.beans.factory.annotation.Autowired

@Saga // 기본생성자 필요
class OrderSaga(
) {

    @Transient
    @Autowired private lateinit var  commandGateway: CommandGateway // 직렬화 제외해야함

    @Transient
    private val logger = KotlinLogging.logger {}

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    fun create(event: OrderCreatedEvent) {
        logger.info { "주문생성사가 시작: event: ${event}" }

        val command = DecreaseStockQuantityCommand(event.orderId, event.itemId, event.quantity)

        SagaLifecycle.associateWith("itemId", event.itemId)

        commandGateway.send<Any>(command)
    }

    @SagaEventHandler(associationProperty = "itemId")
    fun on(event: DecreaseStockQuantityApprovedEvent) {
        SagaLifecycle.associateWith("orderId", event.orderId)

        commandGateway.send<Any>(PrepareShippingCommand(event.orderId))
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    fun on(event: OrderShippingPreparedEvent) {
        logger.info { "주문생성 사가 종료: ${event}" }
    }

    @SagaEventHandler(associationProperty = "itemId")
    fun on(event: DecreaseStockQuantityRejectedEvent) {
        logger.info { "주문생성 중 재고감소 실패: ${event}" }
        SagaLifecycle.end()
    }
}
