package com.transfer.money.domain

import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table
class Account(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val username: String,
    var balance: BigDecimal
)