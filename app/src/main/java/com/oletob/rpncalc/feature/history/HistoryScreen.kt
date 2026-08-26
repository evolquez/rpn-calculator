package com.oletob.rpncalc.feature.history

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oletob.rpncalc.R
import com.oletob.rpncalc.data.local.MathOperation
import com.oletob.rpncalc.ui.theme.Accent
import com.oletob.rpncalc.ui.theme.AppBackground
import com.oletob.rpncalc.ui.theme.Divider as DividerColor
import com.oletob.rpncalc.ui.theme.Muted
import com.oletob.rpncalc.ui.theme.PanelBackground
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    onNavigateBack: () -> Unit,
    viewModel: HistoryViewModel = hiltViewModel()
) {
    val history by viewModel.history.collectAsStateWithLifecycle()
    var showClearConfirmation by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        containerColor = AppBackground,
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.history), color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, tint = Color.White)
                    }
                },
                actions = {
                    OutlinedButton(
                        onClick = { showClearConfirmation = true },
                        shape = RoundedCornerShape(50),
                        border = BorderStroke(1.dp, Accent),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Accent),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(stringResource(R.string.clear_all), fontSize = 13.sp)
                    }
                    Spacer(Modifier.width(16.dp))
                },
                //colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = AppBackground)
            )
        }
    ) { padding ->
        if (history.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text(text = stringResource(R.string.no_history), color = Muted)
            }
        } else {
            val todayLabel = stringResource(R.string.history_today)
            val yesterdayLabel = stringResource(R.string.history_yesterday)
            val grouped = remember(history, todayLabel, yesterdayLabel) {
                history.groupBy { dateLabel(it.timestamp, todayLabel, yesterdayLabel) }
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                grouped.forEach { (label, operations) ->
                    item(key = "header_$label") { SectionHeader(label) }
                    item(key = "group_$label") { HistoryGroupCard(operations) }
                }
                item(key = "footer") { EndOfHistoryFooter() }
            }
        }
    }

    if (showClearConfirmation) {
        AlertDialog(
            onDismissRequest = { showClearConfirmation = false },
            containerColor = PanelBackground,
            text = { Text(stringResource(R.string.clear_history_message)) },
            confirmButton = {
                TextButton(onClick = {
                    showClearConfirmation = false
                    viewModel.clearHistory()
                }) { Text(stringResource(R.string.yes)) }
            },
            dismissButton = {
                TextButton(onClick = { showClearConfirmation = false }) { Text(stringResource(R.string.no)) }
            }
        )
    }
}

@Composable
private fun SectionHeader(label: String) {
    Text(
        text = label.uppercase(),
        color = Muted,
        fontSize = 12.sp,
        letterSpacing = 1.sp,
        modifier = Modifier.padding(start = 4.dp)
    )
}

@Composable
private fun HistoryGroupCard(operations: List<MathOperation>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(PanelBackground)
    ) {
        operations.forEachIndexed { index, operation ->
            HistoryRow(operation)
            if (index != operations.lastIndex) {
                HorizontalDivider(color = DividerColor, modifier = Modifier.padding(horizontal = 16.dp))
            }
        }
    }
}

@Composable
private fun HistoryRow(operation: MathOperation) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = operation.statement, color = Muted, fontSize = 17.sp, modifier = Modifier.weight(1f))
        Text(text = "=", color = Muted, fontSize = 15.sp, modifier = Modifier.padding(horizontal = 8.dp))
        Text(text = operation.result, color = Accent, fontWeight = FontWeight.Bold, fontSize = 20.sp)
    }
}

@Composable
private fun EndOfHistoryFooter() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth().padding(vertical = 24.dp)
    ) {
        HorizontalDivider(modifier = Modifier.width(60.dp), color = DividerColor)
        Text(
            text = stringResource(R.string.end_of_history),
            color = Muted,
            fontSize = 12.sp,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

private fun dateLabel(
    timestampMillis: Long,
    todayLabel: String,
    yesterdayLabel: String,
    now: Long = System.currentTimeMillis()
): String {
    fun startOfDay(millis: Long): Long {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = millis
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }

    val oneDayMillis = 24 * 60 * 60 * 1000L
    val dayDiff = startOfDay(now) - startOfDay(timestampMillis)
    return when (dayDiff) {
        0L -> todayLabel
        oneDayMillis -> yesterdayLabel
        else -> SimpleDateFormat("MMM d", Locale.getDefault()).format(Date(timestampMillis))
    }
}
