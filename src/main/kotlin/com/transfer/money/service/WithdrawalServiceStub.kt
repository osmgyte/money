package com.transfer.money.service

import com.transfer.money.domain.MoneyTransaction
import com.transfer.money.exception.AccountNotFoundException
import com.transfer.money.exception.InsufficientBalanceException
import com.transfer.money.exception.WithdrawalAlreadyExistException
import com.transfer.money.exception.WithdrawalNotFoundException
import com.transfer.money.model.WithdrawalModel
import com.transfer.money.repository.AccountRepository
import com.transfer.money.service.WithdrawalService.WithdrawalId
import com.transfer.money.service.WithdrawalService.WithdrawalState
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import kotlin.random.Random


@Service
class WithdrawalServiceStub(
        private val accountRepository: AccountRepository
) : WithdrawalService {

    private val requests: ConcurrentMap<WithdrawalId, WithdrawalModel> = ConcurrentHashMap()

    override fun requestWithdrawal(withdrawalId: WithdrawalId, senderId:Long, address: WithdrawalService.Address, amount: BigDecimal) {
        if (amount <= BigDecimal(0)) {
            throw InsufficientBalanceException("Transfer amount cannot be equal or lower than 0!")
        }

        var request = requests[withdrawalId]
        if (request != null && request.address == address && request.amount == amount) {
            throw WithdrawalAlreadyExistException("Withdrawal already exist!")
        }

        val senderAccountEntity = accountRepository.findById(senderId)
        if (senderAccountEntity.isEmpty) {
            throw AccountNotFoundException("Account not found for sender: ${senderId}")
        }

        val senderAccount = senderAccountEntity.get()
        if (senderAccount.balance < amount){
            throw InsufficientBalanceException("Insufficient Balance!")
        }
        synchronized (senderAccount) {
            senderAccount.balance = senderAccount.balance.minus(amount)
            accountRepository.save(senderAccount)
        }

        requests[withdrawalId] = WithdrawalModel(senderAccount, address, amount, finaliseAt())
    }

    override fun getRequestState(id: WithdrawalId): WithdrawalState {
        val request = requests[id] ?: throw WithdrawalNotFoundException("Withdrawal not found for: $id")

        return if (request.finaliseAt <= System.currentTimeMillis()) {
            if (request.state == WithdrawalService.WithdrawalState.NOT_STARTED) {
                request.state = finalState()
            }
            request.state
        } else {
            WithdrawalService.WithdrawalState.PROCESSING
        }
    }

    private fun finalState(): WithdrawalState {
        return if (Random.nextBoolean()) WithdrawalState.COMPLETED else WithdrawalState.FAILED
    }

    private fun finaliseAt(): Long {
        return System.currentTimeMillis() + Random.nextInt(1000, 10000)
    }

}
