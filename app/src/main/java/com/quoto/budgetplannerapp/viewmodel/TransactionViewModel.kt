package com.quoto.budgetplannerapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.quoto.budgetplannerapp.data.BudgetDataStore
import com.quoto.budgetplannerapp.data.TransactionPlanner
import com.quoto.budgetplannerapp.datastore.CategoryLimitPreferences
import com.quoto.budgetplannerapp.repo.TransactionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TransactionViewModel(private val repository: TransactionRepository, private val budgetDataStore: BudgetDataStore, private val categoryLimitPreferences: CategoryLimitPreferences

) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    val groceriesLimit: StateFlow<Double> = categoryLimitPreferences.groceriesLimitFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    val transportLimit: StateFlow<Double> = categoryLimitPreferences.transportLimitFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    private val _allTransactions: StateFlow<List<TransactionPlanner>> =
        repository.allTransactions
            .onStart { _isLoading.value = true }
            .onEach { _isLoading.value = false }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )

    val allTransactions: StateFlow<List<TransactionPlanner>> = _allTransactions
    val monthlyBudget: StateFlow<Double> = budgetDataStore.budgetFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        1000.0
    )
    fun updateBudget(newBudget: Double) = viewModelScope.launch {
        budgetDataStore.saveBudget(newBudget)
    }
    fun insertTransaction(title: String, amount: Double, category: String) {
        viewModelScope.launch {
            val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val transaction = TransactionPlanner(title = title, amount = amount, date = currentDate, category = category)
            repository.insert(transaction)
        }
    }

    fun saveGroceriesLimit(limit: Double) = viewModelScope.launch {
        categoryLimitPreferences.saveGroceriesLimit(limit)
    }

    fun saveTransportLimit(limit: Double) = viewModelScope.launch {
        categoryLimitPreferences.saveTransportLimit(limit)
    }
    fun deleteTransaction(transactionPlanner: TransactionPlanner) = viewModelScope.launch {
        repository.delete(transactionPlanner)
    }
}
