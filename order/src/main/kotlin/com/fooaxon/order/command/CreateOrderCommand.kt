package com.fooaxon.order.command

import com.fooaxon.order.entity.OrderStatus
import com.fooaxon.order.event.OrderCreatedEvent
import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.math.BigDecimal

data class CreateOrderCommand(
    @TargetAggregateIdentifier
    val orderId: String,
    val itemName: String,
    val itemId: String,
    val quantity: Int,
    val price: BigDecimal,
) {
    fun toEvent(
        orderId: String,
        orderStatus: OrderStatus,
    ): OrderCreatedEvent {
        return OrderCreatedEvent(
            orderId = orderId,
            orderStatus = orderStatus,
            itemName = itemName,
            quantity = quantity,
            price = price,
            itemId = itemId,
        )
    }

}
