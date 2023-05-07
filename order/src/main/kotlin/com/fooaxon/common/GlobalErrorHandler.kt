package com.fooaxon.common

import mu.KotlinLogging
import org.axonframework.axonserver.connector.command.AxonServerRemoteCommandHandlingException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalErrorHandler {

    private val logger = KotlinLogging.logger {}

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    fun handle(e: AxonServerRemoteCommandHandlingException): String {
        logger.info(e) {}
        return e.message ?: "에러"
    }

}
