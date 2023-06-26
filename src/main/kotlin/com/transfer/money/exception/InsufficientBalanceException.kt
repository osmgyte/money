package com.transfer.money.exception

open class InsufficientBalanceException(override val message: String) : RuntimeException(message)