package com.transfer.money.domain

import javax.persistence.*

@Entity
@Table
class MoneyTransaction (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    val sender: Account,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    val receiver: Account,

    val timestamp: Long = System.currentTimeMillis()
)