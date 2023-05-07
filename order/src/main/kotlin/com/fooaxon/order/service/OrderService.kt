package com.fooaxon.order.service

import com.fooaxon.order.controller.dto.ChangeOrderItemInfoRequest
import com.fooaxon.order.controller.dto.CreateOrderRequest
import mu.KotlinLogging
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.TimeUnit

@Service
class OrderService(
    private val commandGateway: CommandGateway,
) {

    private val logger = KotlinLogging.logger {}

    fun create(request: CreateOrderRequest): String {
        logger.info { "주문 생성 요청: ${request}" }

        val orderId = UUID.randomUUID().toString()
        val command = request.toCommand(orderId)

        logger.info { "주문 생성 커맨드 발행" }
        return commandGateway.sendAndWait(command, 1, TimeUnit.SECONDS)
    }

    fun changeItemInfo(orderId: String, request: ChangeOrderItemInfoRequest) {
        logger.info { "주문상품정보 변경 요청${orderId}" }

        logger.info { "주문상품정보 커맨드 발행: ${orderId}" }
        commandGateway.sendAndWait<String>(request.toCommand(orderId))
    }
}
