package com.fooaxon.controller

import com.fooaxon.controller.dto.CreateItemRequest
import com.fooaxon.service.ItemService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ItemController(
    private val itemService: ItemService,
) {

    @PostMapping
    fun createItem(@RequestBody request: CreateItemRequest): String {
        return itemService.create(request.itemName, request.stockQuantity)
    }
}