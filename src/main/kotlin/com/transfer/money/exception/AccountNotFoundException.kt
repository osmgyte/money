package com.transfer.money.exception

open class AccountNotFoundException(override val message: String) : RuntimeException(message)