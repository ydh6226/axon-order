package com.fooaxon.order.event

import java.math.BigDecimal

data class OrderItemInfoChangedEvent(
    val orderId: String,
    val quantity: Int,
    val price: BigDecimal,
) {

}
