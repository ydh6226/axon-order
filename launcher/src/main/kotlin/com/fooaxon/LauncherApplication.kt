package com.fooaxon

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LauncherApplication

fun main(args: Array<String>) {
    runApplication<LauncherApplication>(*args)
}
