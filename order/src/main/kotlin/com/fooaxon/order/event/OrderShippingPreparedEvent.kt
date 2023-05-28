package com.fooaxon.order.event

import com.fooaxon.order.entity.OrderStatus

class OrderShippingPreparedEvent(
    val orderId: String,
    val orderStatus: OrderStatus,
)