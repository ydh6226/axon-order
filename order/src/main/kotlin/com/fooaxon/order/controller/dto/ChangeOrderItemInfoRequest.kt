package com.fooaxon.order.controller.dto

import com.fooaxon.order.command.ChangeOrderItemInfoCommand
import java.math.BigDecimal

data class ChangeOrderItemInfoRequest(
    val quantity: Int,
    val price: BigDecimal,
) {

    fun toCommand(orderId: String): ChangeOrderItemInfoCommand {
        return ChangeOrderItemInfoCommand(
            orderId = orderId,
            quantity = quantity,
            price = price,
        )
    }

}
