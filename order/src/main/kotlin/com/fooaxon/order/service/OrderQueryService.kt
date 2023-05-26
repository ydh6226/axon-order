package com.fooaxon.order.service

import mu.KotlinLogging
import org.axonframework.config.Configuration
import org.axonframework.eventhandling.TrackingEventProcessor
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class OrderQueryService(
    private val configuration: Configuration,
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
}