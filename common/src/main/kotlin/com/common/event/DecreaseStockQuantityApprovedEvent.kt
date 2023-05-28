package com.common.event

class DecreaseStockQuantityApprovedEvent(
    val itemId: String,
    val stockQuantity: Int,
    val orderId: String,

) {
}