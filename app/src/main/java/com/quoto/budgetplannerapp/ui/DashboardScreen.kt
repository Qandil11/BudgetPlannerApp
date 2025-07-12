package com.quoto.budgetplannerapp.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.quoto.budgetplannerapp.data.TransactionPlanner
import com.quoto.budgetplannerapp.ui.components.BannerAdView

enum class FilterOption { ALL, INCOME, EXPENSE }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    onAddTransactionClick: () -> Unit,
    onSettingsClick: () -> Unit,
    transactions: List<TransactionPlanner>
) {
    var filterOption by remember { mutableStateOf(FilterOption.ALL) }

    val filteredTransactions = when (filterOption) {
        FilterOption.ALL -> transactions
        FilterOption.INCOME -> transactions.filter { it.amount > 0 }
        FilterOption.EXPENSE -> transactions.filter { it.amount < 0 }
    }

    val totalIncome = transactions.filter { it.amount > 0 }.sumOf { it.amount }
    val totalExpense = transactions.filter { it.amount < 0 }.sumOf { it.amount }
    val totalBalance = totalIncome + totalExpense
    val monthlyBudget = 1000.0
    val progress = (totalExpense / monthlyBudget).coerceIn(0.0, 1.0)

    Scaffold(
        topBar = {
            BudgetTopBar(
                title = "Budget Planner",
                showSettings = true,
                onSettingsClick = onSettingsClick
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Add") },
                icon = { Text("+") },
                onClick = onAddTransactionClick,
                containerColor = MaterialTheme.colorScheme.primary
            )
        },
        modifier = modifier
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                SummaryCard(totalBalance, totalIncome, totalExpense)

                Spacer(modifier = Modifier.height(16.dp))
                Text("Budget Usage", fontWeight = FontWeight.Medium)
                LinearProgressIndicator(
                    progress = progress.toFloat(),
                    modifier = Modifier.fillMaxWidth()
                )
                Text("\u00A3${-totalExpense} / \u00A3$monthlyBudget", color = Color.Gray)

                Spacer(modifier = Modifier.height(16.dp))

                FilterChips(filterOption) { selected ->
                    filterOption = selected
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text("Recent Transactions", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)

                LazyColumn(
                    modifier = Modifier.weight(1f, fill = false).padding(bottom = 72.dp) // create room for FAB and Ad

                ) {
                    items(filteredTransactions) { tx ->
                        TransactionCard(tx)
                    }
                }

                Spacer(modifier = Modifier.height(48.dp)) // Optional space above ad
            }
            Spacer(modifier = Modifier.height(8.dp))
            Divider(thickness = 1.dp, color = Color.LightGray)

            BannerAdView(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun SummaryCard(balance: Double, income: Double, expense: Double) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier.fillMaxWidth(),elevation = CardDefaults.cardElevation(6.dp)

    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Balance", fontWeight = FontWeight.Bold, color = Color.Black)
                Text("\u00A3%.2f".format(balance), fontWeight = FontWeight.Bold)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Income", color = Color(0xFF2E7D32))
                Text("+\u00A3%.2f".format(income), color = Color(0xFF2E7D32))
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Expense", color = Color.Red)
                Text(
                    text = if (expense < 0) "-\u00A3%.2f".format(-expense) else "\u00A3%.2f".format(0.0),
                    color = Color.Red
                )            }
        }
    }
}

@Composable
fun FilterChips(selected: FilterOption, onSelected: (FilterOption) -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        FilterChip("All", selected == FilterOption.ALL) { onSelected(FilterOption.ALL) }
        FilterChip("Income", selected == FilterOption.INCOME) { onSelected(FilterOption.INCOME) }
        FilterChip("Expense", selected == FilterOption.EXPENSE) { onSelected(FilterOption.EXPENSE) }
    }
}

@Composable
fun FilterChip(text: String, selected: Boolean, onClick: () -> Unit) {
    AssistChip(
        onClick = onClick,
        label = { Text(text) },
        colors = AssistChipDefaults.assistChipColors(
            containerColor = if (selected) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f) else Color.Transparent
        )
    )
}

@Composable
fun TransactionCard(tx: TransactionPlanner) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(tx.title, fontWeight = FontWeight.Bold)
                Text(
                    text = if (tx.amount < 0) "-\u00A3${-tx.amount}" else "+\u00A3${tx.amount}",
                    color = if (tx.amount < 0) Color.Red else Color(0xFF388E3C),
                    fontWeight = FontWeight.SemiBold
                )
            }
            Text(tx.date, color = Color.Gray, fontSize = 12.sp)
        }
    }
}
