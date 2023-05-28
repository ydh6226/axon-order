package com.fooaxon.order.controller

import com.fooaxon.order.controller.dto.ChangeOrderItemInfoRequest
import com.fooaxon.order.controller.dto.CreateOrderRequest
import com.fooaxon.order.controller.dto.CreateOrderResponse
import com.fooaxon.order.entity.Order
import com.fooaxon.order.service.OrderQueryService
import com.fooaxon.order.service.OrderService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/orders")
class OrderController(
    private val orderService: OrderService,
    private val orderQueryService: OrderQueryService,
) {

    @GetMapping("/{orderId}")
    fun find(@PathVariable orderId: String): Order {
        return orderQueryService.findOrder(orderId)
    }

    @PostMapping
    fun create(@RequestBody request: CreateOrderRequest): CreateOrderResponse {
        val orderId = orderService.create(request)
        return CreateOrderResponse(orderId)
    }

    @PatchMapping("/{orderId}")
    fun changeItemInfo(
        @PathVariable orderId: String,
        @RequestBody request: ChangeOrderItemInfoRequest,
    ): Boolean {
        orderService.changeItemInfo(orderId, request)
        return true
    }

    @PostMapping("/projections/reset")
    fun deleteAll() {
        // https://cla9.tistory.com/17 리플레이 최적화
        // TODO: 단건으로만 리플레이 가능한가?
        orderQueryService.reset()
    }

}
