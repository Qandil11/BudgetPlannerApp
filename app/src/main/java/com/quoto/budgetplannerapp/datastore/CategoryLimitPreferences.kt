package com.quoto.budgetplannerapp.datastore

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("category_limits")

class CategoryLimitPreferences(private val context: Context) {
    companion object {
        val GROCERIES_LIMIT = doublePreferencesKey("groceries_limit")
        val TRANSPORT_LIMIT = doublePreferencesKey("transport_limit")
    }

    val groceriesLimitFlow: Flow<Double> = context.dataStore.data
        .map { it[GROCERIES_LIMIT] ?: 0.0 }

    val transportLimitFlow: Flow<Double> = context.dataStore.data
        .map { it[TRANSPORT_LIMIT] ?: 0.0 }

    suspend fun saveGroceriesLimit(value: Double) {
        context.dataStore.edit { it[GROCERIES_LIMIT] = value }
    }

    suspend fun saveTransportLimit(value: Double) {
        context.dataStore.edit { it[TRANSPORT_LIMIT] = value }
    }
}
