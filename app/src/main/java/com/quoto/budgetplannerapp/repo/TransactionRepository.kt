package com.quoto.budgetplannerapp.repo

import com.quoto.budgetplannerapp.dao.TransactionDao
import com.quoto.budgetplannerapp.data.TransactionPlanner

class TransactionRepository(private val dao: TransactionDao) {

    val allTransactions = dao.getAllTransactions()

    suspend fun insert(transactionPlanner: TransactionPlanner) {
        dao.insert(transactionPlanner)
    }

    suspend fun delete(transactionPlanner: TransactionPlanner) {
        dao.delete(transactionPlanner)
    }


}
