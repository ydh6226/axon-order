package com.fooaxon.service

import com.fooaxon.command.CreateItemCommand
import mu.KotlinLogging
import org.apache.commons.lang3.exception.ExceptionUtils
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.TimeUnit

@Service
class ItemService(
    private val commandGateway: CommandGateway,
) {

    private val logger = KotlinLogging.logger {}

    fun create(
        itemName: String,
        stockQuantity: Int,
    ): String {

        val itemId = UUID.randomUUID().toString()
        val command = CreateItemCommand(itemId, itemName, stockQuantity)
        return try {
            commandGateway.sendAndWait(command, 1, TimeUnit.SECONDS)
        } catch (e: Exception) {
            val root = ExceptionUtils.getRootCause(e)
            throw e
        }
    }
}