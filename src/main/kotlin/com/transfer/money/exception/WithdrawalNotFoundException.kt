package com.transfer.money.exception

open class WithdrawalNotFoundException(override val message: String) : RuntimeException(message)