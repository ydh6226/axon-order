package com.fooaxon.order.command

import org.axonframework.modelling.command.TargetAggregateIdentifier

data class PrepareShippingCommand(
    @TargetAggregateIdentifier
    val orderId: String,
)