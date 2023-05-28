package com.fooaxon.order.service

import com.fooaxon.order.dto.OrderQuery
import com.fooaxon.order.entity.Order
import mu.KotlinLogging
import org.axonframework.config.Configuration
import org.axonframework.eventhandling.TrackingEventProcessor
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.QueryGateway
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException.BadRequest
import javax.annotation.PostConstruct

@Service
class OrderQueryService(
    private val configuration: Configuration,
    private val queryGateway: QueryGateway,
) {

    private val logger = KotlinLogging.logger {}

    fun reset() {
        configuration.eventProcessingConfiguration()
            .eventProcessorByProcessingGroup("orders", TrackingEventProcessor::class.java)
            .ifPresent {
                it.shutDown()
                it.resetTokens()
                it.start()
            }
    }

    fun findOrder(orderId: String): Order {
        val query = OrderQuery(orderId)
        return try {
            queryGateway.query(query, ResponseTypes.instanceOf(Order::class.java)).join()
        } catch (e: Exception) {
            logger.info(e) { "주문조회 중 에러발생: ${e.message}" }
            throw IllegalArgumentException(e)
        }
    }

    // TODO: 변경을 구독하는 방식, scatter-gather 방식도 있음. https://cla9.tistory.com/20
}