package com.transfer.money.service

import com.transfer.money.domain.Account
import com.transfer.money.domain.MoneyTransaction
import com.transfer.money.exception.AccountNotFoundException
import com.transfer.money.exception.InsufficientBalanceException
import com.transfer.money.model.AccountCreateRequest
import com.transfer.money.model.MoneyTransferRequest
import com.transfer.money.repository.AccountRepository
import com.transfer.money.repository.MoneyTransactionRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.util.*
import kotlin.random.Random

@Service
class TransactionService(
    private val moneyTransactionRepository: MoneyTransactionRepository,
    private val accountRepository: AccountRepository
) {

    companion object {
        private val logger = LoggerFactory.getLogger(TransactionService::class.java)
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    fun sendMoney(moneyTransferRequest: MoneyTransferRequest): String {
        val senderAccountEntity = accountRepository.findById(moneyTransferRequest.senderId)
        if (senderAccountEntity.isEmpty) {
            throw AccountNotFoundException("Account not found for sender: ${moneyTransferRequest.senderId}")
        }
        val senderAccount = senderAccountEntity.get()

        val receiverAccountEntity = accountRepository.findById(moneyTransferRequest.receiverId)
        if (receiverAccountEntity.isEmpty) {
            throw AccountNotFoundException("Account not found for receiver: ${moneyTransferRequest.receiverId}")
        }
        val receiverAccount = receiverAccountEntity.get()


        updateBalances(senderAccount, receiverAccount, moneyTransferRequest.amount)
        return UUID.randomUUID().toString()
    }

    fun getAllTransactions(): List<MoneyTransaction> {
        return moneyTransactionRepository.findAll()
    }

    fun createAccount(accountCreateRequest: AccountCreateRequest) {
        accountRepository.save(
            Account(null, accountCreateRequest.name, Random.nextInt(1000).toBigDecimal())
        )
    }

    fun getAllAccounts(): List<Account> {
        return accountRepository.findAll()
    }

    private fun hasSufficientBalance(senderAccount: Account, amount: BigDecimal): Boolean {
        return senderAccount.balance >= amount
    }

    private fun updateBalances(senderAccount: Account, receiverAccount: Account, amount: BigDecimal) {
        if (amount <= BigDecimal(0)) {
            throw InsufficientBalanceException("Transfer amount cannot be equal or lower than 0!")
        }
        if (hasSufficientBalance(senderAccount, amount).not()) {
            throw InsufficientBalanceException("Insufficient Balance!")
        }

        synchronized (senderAccount) {
            synchronized (receiverAccount) {
                senderAccount.balance = senderAccount.balance.minus(amount)
                accountRepository.save(senderAccount)

                receiverAccount.balance = receiverAccount.balance.plus(amount)
                accountRepository.save(receiverAccount)

                moneyTransactionRepository.save(MoneyTransaction(null, senderAccount, receiverAccount))
            }
        }
    }
}