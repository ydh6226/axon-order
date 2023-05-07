package com.fooaxon.order.command

import com.fooaxon.order.event.OrderItemInfoChangedEvent
import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.math.BigDecimal

data class ChangeOrderItemInfoCommand(
    @TargetAggregateIdentifier
    val orderId: String,
    val quantity: Int,
    val price: BigDecimal,
) {

    fun toEvent(): OrderItemInfoChangedEvent {
        return OrderItemInfoChangedEvent(
            orderId = orderId,
            quantity = quantity,
            price = price,
        )
    }
}
