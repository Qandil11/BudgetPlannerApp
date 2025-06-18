package com.quoto.budgetplannerapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.quoto.budgetplannerapp.viewmodel.TransactionViewModel
import kotlinx.coroutines.launch
import androidx.compose.ui.text.input.KeyboardType // <-- Add at the top
import android.content.Context
import androidx.compose.ui.platform.LocalContext
import android.content.Intent
import com.quoto.budgetplannerapp.util.exportTransactionsToCSV

@Composable
fun SettingsScreen(
    onBackClick: () -> Unit,
    viewModel: TransactionViewModel
) {
    val budget by viewModel.monthlyBudget.collectAsState()
    val groceriesLimit by viewModel.groceriesLimit.collectAsState()
    val transportLimit by viewModel.transportLimit.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val allTransactions by viewModel.allTransactions.collectAsState()
    // Keep inputs in sync with real values from DataStore
    var budgetInput by remember { mutableStateOf("") }
    var groceriesInput by remember { mutableStateOf("") }
    var transportInput by remember { mutableStateOf("") }

    // Update inputs when values change (e.g. after app restarts)
    LaunchedEffect(budget) {
        budgetInput = budget.toString()
    }
    LaunchedEffect(groceriesLimit) {
        groceriesInput = groceriesLimit.toString()
    }
    LaunchedEffect(transportLimit) {
        transportInput = transportLimit.toString()
    }

    Scaffold(
        topBar = {
            BudgetTopBar(
                title = "Settings",
                showBack = true,
                onBackClick = onBackClick,
                showSettings = false
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Monthly Budget", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            OutlinedTextField(
                value = budgetInput,
                onValueChange = { budgetInput = it },
                label = { Text("Enter budget (£)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            Button(
                onClick = {
                    budgetInput.toDoubleOrNull()?.let {
                        viewModel.updateBudget(it)
                        scope.launch {
                            snackbarHostState.showSnackbar("✅ Budget saved")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Budget")
            }

            Spacer(Modifier.height(24.dp))
            Divider()
            Spacer(Modifier.height(16.dp))

            Text("Category Limits", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            OutlinedTextField(
                value = groceriesInput,
                onValueChange = { groceriesInput = it },
                label = { Text("Groceries Limit (£)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = transportInput,
                onValueChange = { transportInput = it },
                label = { Text("Transport Limit (£)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            Button(
                onClick = {
                    val groceries = groceriesInput.toDoubleOrNull()
                    val transport = transportInput.toDoubleOrNull()
                    if (groceries != null) viewModel.saveGroceriesLimit(groceries)
                    if (transport != null) viewModel.saveTransportLimit(transport)
                    scope.launch {
                        snackbarHostState.showSnackbar("✅ Limits saved")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Limits")
            }

            Spacer(Modifier.height(24.dp))
            Divider()
            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    scope.launch {
                        val exportIntent = exportTransactionsToCSV(context, allTransactions)
                        exportIntent?.let {
                            val chooser = Intent.createChooser(it, "Export to CSV")

                            // 🔑 Manually grant URI permission to all potential receiving apps
                            val resInfoList = context.packageManager.queryIntentActivities(chooser, 0)
                            resInfoList.forEach { resolveInfo ->
                                val packageName = resolveInfo.activityInfo.packageName
                                context.grantUriPermission(
                                    packageName,
                                    it.getParcelableExtra(Intent.EXTRA_STREAM),
                                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                                )
                            }

                            context.startActivity(chooser)
                        } ?: snackbarHostState.showSnackbar("No data to export")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Export Data")
            }


        }
    }
}
