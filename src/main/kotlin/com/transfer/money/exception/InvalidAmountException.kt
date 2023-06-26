package com.transfer.money.exception

open class InvalidAmountException(override val message: String) : RuntimeException(message)