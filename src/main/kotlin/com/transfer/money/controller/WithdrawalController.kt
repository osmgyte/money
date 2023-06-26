package com.transfer.money.controller

import com.transfer.money.domain.MoneyTransaction
import com.transfer.money.model.WithdrawalRequest
import com.transfer.money.service.WithdrawalService
import com.transfer.money.service.WithdrawalServiceStub
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("api/v1")
class WithdrawalController(
    private val withdrawalServiceStub: WithdrawalServiceStub
) {
    companion object {
        private val logger = LoggerFactory.getLogger(WithdrawalController::class.java)
    }

    @PostMapping("/withdrawal")
    fun requestWithdrawal(@RequestBody request: WithdrawalRequest): ResponseEntity<List<MoneyTransaction>> {
        logger.info("withdrawing from: ${request.senderId} to: ${request.address}")
        withdrawalServiceStub.requestWithdrawal(request.withdrawalId, request.senderId, request.address, request.amount)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/withdrawal/{withdrawalId}")
    fun sendMoney(
        @PathVariable withdrawalId: String
    ): ResponseEntity<WithdrawalService.WithdrawalState> {
        logger.info("get withdrawal status for:$withdrawalId")
        val response = withdrawalServiceStub.getRequestState(
            WithdrawalService.WithdrawalId(UUID.fromString(withdrawalId))
        )
        return ResponseEntity.ok(response)
    }
}
