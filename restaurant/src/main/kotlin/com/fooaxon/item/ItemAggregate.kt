package com.fooaxon.item

import com.fooaxon.command.CreateItemCommand
import mu.KotlinLogging
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.spring.stereotype.Aggregate
import javax.persistence.*

@Entity(name = "items")
@Table(name = "items")
@Aggregate
class ItemAggregate(

    @AggregateIdentifier
    val itemId: String,

    val itemName: String,

    var stockQuantity: Int,
) {

    @Transient
    private val logger = KotlinLogging.logger {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @CommandHandler
    constructor(command: CreateItemCommand): this(command.itemId, command.itemName, command.stockQuantity) {
        logger.info { "상품등록: ${command}" }
        check(command.stockQuantity >= 0) {"재고는 0개 이상이어야합니다. ${command}"}
    }

}