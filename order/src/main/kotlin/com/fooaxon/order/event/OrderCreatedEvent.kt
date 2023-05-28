package com.fooaxon.order.event

import com.fooaxon.order.entity.OrderStatus
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

class OrderCreatedEvent(
    val orderId: String,
    val orderStatus: OrderStatus,
    val itemName: String,
    val itemId: String = "",
    val quantity: Int,
    val price: BigDecimal,
    val eventId: String = UUID.randomUUID().toString(),
    val createdAt: LocalDateTime = LocalDateTime.now(),
)
