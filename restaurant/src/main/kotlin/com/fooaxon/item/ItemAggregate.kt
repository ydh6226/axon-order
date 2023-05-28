package com.fooaxon.item

import com.common.command.DecreaseStockQuantityCommand
import com.common.event.DecreaseStockQuantityApprovedEvent
import com.common.event.DecreaseStockQuantityRejectedEvent
import com.fooaxon.command.CreateItemCommand
import mu.KotlinLogging
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate
import javax.persistence.*
import kotlin.jvm.Transient

@Entity(name = "items")
@Table(name = "items")
@Aggregate
class ItemAggregate(

    @Id
    @AggregateIdentifier
    val itemId: String,

    val itemName: String,

    var stockQuantity: Int,
) {

    @Transient
    private val logger = KotlinLogging.logger {}

    @CommandHandler
    constructor(command: CreateItemCommand): this(command.itemId, command.itemName, command.stockQuantity) {
        logger.info { "상품등록: ${command}" }
        check(command.stockQuantity >= 0) {"재고는 0개 이상이어야합니다. ${command}"}
    }

    @CommandHandler
    fun on(command: DecreaseStockQuantityCommand) {
        logger.info { "재고감소: ${command}" }
        val remainQuantity = stockQuantity - command.stockQuantity
        if (remainQuantity >= 0) {
            stockQuantity -= command.stockQuantity

            AggregateLifecycle.apply(DecreaseStockQuantityApprovedEvent(itemId, command.stockQuantity, command.orderId))
        } else {
            AggregateLifecycle.apply(DecreaseStockQuantityRejectedEvent(itemId, "재고가 부족합니다."))
        }
    }

}