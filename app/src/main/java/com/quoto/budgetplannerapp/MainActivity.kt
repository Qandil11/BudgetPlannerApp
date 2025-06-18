package com.quoto.budgetplannerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.quoto.budgetplannerapp.data.BudgetDataStore
import com.quoto.budgetplannerapp.database.AppDatabase
import com.quoto.budgetplannerapp.datastore.CategoryLimitPreferences
import com.quoto.budgetplannerapp.repo.TransactionRepository
import com.quoto.budgetplannerapp.ui.DashboardScreen
import com.quoto.budgetplannerapp.ui.navigation.AppNavGraph
import com.quoto.budgetplannerapp.ui.theme.BudgetPlannerAppTheme
import com.quoto.budgetplannerapp.viewmodel.TransactionViewModel
import com.quoto.budgetplannerapp.viewmodel.TransactionViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ 1. Set up Room + Repository + ViewModel
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "budget_planner_db"
        ).build()

        val repository = TransactionRepository(db.transactionDao())
        val budgetDataStore = BudgetDataStore(applicationContext)
        val categoryLimitPreferences = CategoryLimitPreferences(applicationContext)

        val viewModelFactory = TransactionViewModelFactory(repository, budgetDataStore, categoryLimitPreferences)
        val viewModel = ViewModelProvider(this, viewModelFactory)[TransactionViewModel::class.java]

        // ✅ 2. Pass ViewModel down to Composables
        enableEdgeToEdge()
        setContent {
            BudgetPlannerAppTheme {
                val navController = rememberNavController()
                AppNavGraph(
                    navController = navController,
                    transactionViewModel = viewModel
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BudgetPlannerAppTheme {
        Greeting("Android")
    }
}