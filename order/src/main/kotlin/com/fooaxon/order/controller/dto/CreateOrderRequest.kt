package com.fooaxon.order.controller.dto

import com.fooaxon.order.command.CreateOrderCommand
import java.math.BigDecimal

data class CreateOrderRequest(
    val itemName: String,
    val quantity: Int,
    val price: BigDecimal,
) {

    fun toCommand(orderId: String): CreateOrderCommand {
        return CreateOrderCommand(
            orderId = orderId,
            itemName = itemName,
            quantity = quantity,
            price = price,
        )
    }
}
