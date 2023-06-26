package com.transfer.money.repository

import com.transfer.money.domain.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AccountRepository : JpaRepository<Account, Long> {
//    fun findById(id: Long): Optional<Account>
}