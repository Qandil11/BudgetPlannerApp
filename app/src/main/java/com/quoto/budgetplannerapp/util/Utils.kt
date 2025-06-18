// file: com.quoto.budgetplannerapp.util.CsvUtils.kt
package com.quoto.budgetplannerapp.util

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import com.quoto.budgetplannerapp.data.TransactionPlanner
import java.io.File

fun exportTransactionsToCSV(context: Context, transactions: List<TransactionPlanner>): Intent? {
    if (transactions.isEmpty()) return null

    val file = File(context.cacheDir, "transactions.csv")
    file.bufferedWriter().use { writer ->
        writer.write("Title,Amount,Date\n")
        transactions.forEach {
            writer.write("${it.title},${it.amount},${it.date}\n")
        }
    }

    val uri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider", // 📦 Make sure this matches Manifest
        file
    )

    return Intent(Intent.ACTION_SEND).apply {
        type = "text/csv"
        putExtra(Intent.EXTRA_STREAM, uri)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
}
