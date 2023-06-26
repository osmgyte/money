package com.transfer.money.controller

import com.transfer.money.domain.Account
import com.transfer.money.domain.MoneyTransaction
import com.transfer.money.model.AccountCreateRequest
import com.transfer.money.model.MoneyTransferRequest
import com.transfer.money.model.MoneyTransferResponse
import com.transfer.money.service.TransactionService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1")
class AccountController(
    private val transactionService: TransactionService
) {

    companion object {
        private val logger = LoggerFactory.getLogger(AccountController::class.java)
    }

    @GetMapping("/transactions")
    fun getAllTransactions(): ResponseEntity<List<MoneyTransaction>> {
        logger.info("get all transactions")
        val response = transactionService.getAllTransactions()
        return ResponseEntity.ok(response)
    }

    @PostMapping("/send-money")
    fun sendMoney(
        @RequestBody moneyTransferRequest: MoneyTransferRequest
    ): ResponseEntity<MoneyTransferResponse> {
        logger.info("sending money from: ${moneyTransferRequest.senderId} to:  ${moneyTransferRequest.receiverId}")
        val response = transactionService.sendMoney(moneyTransferRequest)
        return ResponseEntity.ok(MoneyTransferResponse(response))
    }

    @PostMapping("/create-account")
    fun create(
        @RequestBody accountCreateRequest: AccountCreateRequest
    ): ResponseEntity<Any> {
        logger.info("creating an account for name: ${accountCreateRequest.name}")
        val response = transactionService.createAccount(accountCreateRequest)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/accounts")
    fun getAllAccounts(): ResponseEntity<List<Account>> {
        logger.info("get all accounts")
        val response = transactionService.getAllAccounts()
        return ResponseEntity.ok(response)
    }
}
