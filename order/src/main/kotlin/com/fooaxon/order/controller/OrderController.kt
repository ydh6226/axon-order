package com.fooaxon.order.controller

import com.fooaxon.order.controller.dto.ChangeOrderItemInfoRequest
import com.fooaxon.order.controller.dto.CreateOrderRequest
import com.fooaxon.order.controller.dto.CreateOrderResponse
import com.fooaxon.order.service.OrderService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/orders")
class OrderController(
    private val orderService: OrderService,
) {

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


}
