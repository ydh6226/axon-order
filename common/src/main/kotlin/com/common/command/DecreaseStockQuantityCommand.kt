package com.common.command

import org.axonframework.modelling.command.TargetAggregateIdentifier

class DecreaseStockQuantityCommand(
    val orderId: String,
    @TargetAggregateIdentifier
    val itemId: String,
    val stockQuantity: Int,
) {
}