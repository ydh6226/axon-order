package com.fooaxon.order.entity

import org.hibernate.annotations.OptimisticLocking
import java.math.BigDecimal
import javax.persistence.*

@OptimisticLocking
@Table(name = "orders")
@Entity
class Order(
    val orderId: String,
    @Enumerated(EnumType.STRING)
    var status: OrderStatus = OrderStatus.CREATED,
    val itemName: String,
    val quantity: Int,
    val price: BigDecimal,

    ) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0


    @Version
    private var version: Long = 0
}
