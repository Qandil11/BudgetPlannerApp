package com.quoto.budgetplannerapp.ui.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.quoto.budgetplannerapp.ui.DashboardScreen
import com.quoto.budgetplannerapp.ui.AddTransactionScreen
import com.quoto.budgetplannerapp.viewmodel.TransactionViewModel

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.quoto.budgetplannerapp.ui.SettingsScreen

sealed class Screen(val route: String) {
    object Dashboard : Screen("dashboard")
    object AddTransaction : Screen("add_transaction")
    object Settings : Screen("settings")

}


@Composable
fun AppNavGraph(
    navController: NavHostController,
    transactionViewModel: TransactionViewModel
) {
    val transactions by transactionViewModel.allTransactions.collectAsState()
    val isLoading by transactionViewModel.isLoading.collectAsState()

    NavHost(
        navController = navController,
        startDestination = Screen.Dashboard.route // ✅ use sealed class
    ) {
        composable(Screen.Dashboard.route) {
            DashboardScreen(
                transactions = transactions,
                onAddTransactionClick = {
                    navController.navigate(Screen.AddTransaction.route) // ✅ use sealed class
                },
                onSettingsClick = {
                    navController.navigate(Screen.Settings.route) // ✅ navigate here
                }
            )
        }

        composable(Screen.AddTransaction.route) {
            AddTransactionScreen(
                viewModel = transactionViewModel,
                onTransactionSaved = {
                    navController.popBackStack()
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }


        composable(Screen.Settings.route) {
            val budget by transactionViewModel.monthlyBudget.collectAsState()
            SettingsScreen(
                onBackClick = { navController.popBackStack() },
                viewModel = transactionViewModel

            )
        }
    }

    }

