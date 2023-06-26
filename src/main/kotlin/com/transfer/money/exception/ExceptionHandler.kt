package com.transfer.money.exception

import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class ExceptionHandler {

    companion object {
        private val LOGGER = LoggerFactory.getLogger(ExceptionHandler::class.java)
    }

    @ExceptionHandler(Exception::class)
    fun handleAllExceptions(ex: Exception, request: WebRequest): ResponseEntity<ApiException> {
        log(ex)
        val apiException = ApiException(500, "An unknown Error occurred")
        return ResponseEntity(apiException, HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(AccountNotFoundException::class)
    fun handleDataNotFound(ex: AccountNotFoundException, request: WebRequest): ResponseEntity<ApiException> {
        log(ex)
        val apiException = ApiException(4041, ex.message)
        return ResponseEntity(apiException, HttpHeaders(), HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(InsufficientBalanceException::class)
    fun handleDataNotFound(ex: InsufficientBalanceException, request: WebRequest): ResponseEntity<ApiException> {
        log(ex)
        val apiException = ApiException(4001, ex.message)
        return ResponseEntity(apiException, HttpHeaders(), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(InvalidAmountException::class)
    fun handleDataNotFound(ex: InvalidAmountException, request: WebRequest): ResponseEntity<ApiException> {
        log(ex)
        val apiException = ApiException(4002, ex.message)
        return ResponseEntity(apiException, HttpHeaders(), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(WithdrawalNotFoundException::class)
    fun handleDataNotFound(ex: WithdrawalNotFoundException, request: WebRequest): ResponseEntity<ApiException> {
        log(ex)
        val apiException = ApiException(4042, ex.message)
        return ResponseEntity(apiException, HttpHeaders(), HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(WithdrawalAlreadyExistException::class)
    fun handleDataNotFound(ex: WithdrawalAlreadyExistException, request: WebRequest): ResponseEntity<ApiException> {
        log(ex)
        val apiException = ApiException(4003, ex.message)
        return ResponseEntity(apiException, HttpHeaders(), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleDataNotFound(ex: HttpMessageNotReadableException, request: WebRequest): ResponseEntity<ApiException> {
        log(ex)
        val apiException = ApiException(400, "Http Message Not Readable Exception")
        return ResponseEntity(apiException, HttpHeaders(), HttpStatus.BAD_REQUEST)
    }

    private fun log(ex: Exception) {
        LOGGER.error("Exception caught :: ", ex)
    }
}