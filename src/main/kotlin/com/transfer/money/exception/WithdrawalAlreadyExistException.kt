package com.transfer.money.exception

open class WithdrawalAlreadyExistException(override val message: String) : RuntimeException(message)