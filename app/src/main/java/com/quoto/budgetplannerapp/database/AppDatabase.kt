package com.quoto.budgetplannerapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.quoto.budgetplannerapp.dao.TransactionDao
import com.quoto.budgetplannerapp.data.TransactionPlanner

@Database(entities = [TransactionPlanner::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
}
