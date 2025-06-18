package com.quoto.budgetplannerapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.quoto.budgetplannerapp.data.BudgetDataStore
import com.quoto.budgetplannerapp.datastore.CategoryLimitPreferences
import com.quoto.budgetplannerapp.repo.TransactionRepository

class TransactionViewModelFactory(
    private val repository: TransactionRepository,
    private val budgetDataStore: BudgetDataStore, private val categoryLimitPreferences: CategoryLimitPreferences
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TransactionViewModel(repository, budgetDataStore, categoryLimitPreferences) as T
    }
}
