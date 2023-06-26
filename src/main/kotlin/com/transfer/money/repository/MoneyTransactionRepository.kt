package com.transfer.money.repository

import com.transfer.money.domain.MoneyTransaction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MoneyTransactionRepository : JpaRepository<MoneyTransaction, Long> {
//    fun findByName(name: String): Optional<MoneyTransaction>
}