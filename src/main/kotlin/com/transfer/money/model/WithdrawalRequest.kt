package com.transfer.money.model

import com.transfer.money.service.WithdrawalService
import java.math.BigDecimal

data class WithdrawalRequest(
    val withdrawalId: WithdrawalService.WithdrawalId,
    val senderId: Long,
    val address: WithdrawalService.Address,
    val amount: BigDecimal
)

