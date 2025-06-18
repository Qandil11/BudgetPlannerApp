package com.quoto.budgetplannerapp.dao

import androidx.room.*
import com.quoto.budgetplannerapp.data.TransactionPlanner
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Query("SELECT * FROM transactions")
    fun getAllTransactions(): Flow<List<com.quoto.budgetplannerapp.data.TransactionPlanner>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transactionPlanner: com.quoto.budgetplannerapp.data.TransactionPlanner)

    @Delete
    suspend fun delete(transactionPlanner: TransactionPlanner)
}
