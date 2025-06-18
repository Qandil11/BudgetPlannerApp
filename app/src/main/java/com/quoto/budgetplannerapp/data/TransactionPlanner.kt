package com.quoto.budgetplannerapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionPlanner(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,  // primary key REQUIRED
    val title: String,
    val amount: Double,
    val date: String,
    val category: String // New field

)