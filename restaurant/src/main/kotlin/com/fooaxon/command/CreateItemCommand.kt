package com.fooaxon.command

import org.axonframework.modelling.command.TargetAggregateIdentifier

data class CreateItemCommand(
    @TargetAggregateIdentifier
    val itemId: String,

    val itemName: String,

    var stockQuantity: Int,
)

