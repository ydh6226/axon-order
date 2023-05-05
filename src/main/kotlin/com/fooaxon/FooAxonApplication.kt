package com.fooaxon

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FooAxonApplication

fun main(args: Array<String>) {
    runApplication<FooAxonApplication>(*args)
}
