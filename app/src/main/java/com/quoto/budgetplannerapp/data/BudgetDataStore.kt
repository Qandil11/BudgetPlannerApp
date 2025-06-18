package com.quoto.budgetplannerapp.data

import android.content.Context
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.budgetDataStore by preferencesDataStore("budget_prefs")

class BudgetDataStore(private val context: Context) {

    companion object {
        val MONTHLY_BUDGET_KEY = doublePreferencesKey("monthly_budget")
    }

    val budgetFlow: Flow<Double> = context.budgetDataStore.data.map { prefs ->
        prefs[MONTHLY_BUDGET_KEY] ?: 1000.0 // Default value
    }

    suspend fun saveBudget(budget: Double) {
        context.budgetDataStore.edit { prefs ->
            prefs[MONTHLY_BUDGET_KEY] = budget
        }
    }
}
