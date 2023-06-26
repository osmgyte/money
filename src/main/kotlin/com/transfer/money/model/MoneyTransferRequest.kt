package com.transfer.money.model

import java.math.BigDecimal

data class MoneyTransferRequest(
    val senderId: Long,
    val receiverId: Long,
    val amount: BigDecimal,
)

data class AccountCreateRequest(
    val name: String
)