package com.fooaxon.order.saga

import com.fooaxon.order.event.OrderCreatedEvent
import mu.KotlinLogging
import org.axonframework.commandhandling.gateway.CommandGateway

//@Saga
class OrderSaga(
    private val commandGateway: CommandGateway,
) {

    private val logger = KotlinLogging.logger {}

    //    @StartSaga
//    @SagaEventHandler(associationProperty = "orderId")
    fun create(event: OrderCreatedEvent) {
        logger.info { "주문생성사가 시작: event: ${event}" }
    }

}
