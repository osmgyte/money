package com.transfer.money.service

import java.math.BigDecimal
import java.util.*

interface WithdrawalService {
    fun requestWithdrawal(withdrawalId: WithdrawalId, senderId:Long, address: Address, amount: BigDecimal)
    fun getRequestState(id: WithdrawalId): WithdrawalState

    enum class WithdrawalState {
        PROCESSING, COMPLETED, FAILED, NOT_STARTED
    }

    @JvmRecord
    data class WithdrawalId(val value: UUID)

    @JvmRecord
    data class Address(val value: String)
}

