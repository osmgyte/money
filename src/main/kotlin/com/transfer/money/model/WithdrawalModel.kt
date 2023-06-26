package com.transfer.money.model

import com.transfer.money.domain.Account
import com.transfer.money.service.WithdrawalService
import java.math.BigDecimal

data class WithdrawalModel(
        val senderAccount: Account,
        val address: WithdrawalService.Address,
        val amount: BigDecimal,
        val finaliseAt: Long,
        var state: WithdrawalService.WithdrawalState = WithdrawalService.WithdrawalState.NOT_STARTED
) {}