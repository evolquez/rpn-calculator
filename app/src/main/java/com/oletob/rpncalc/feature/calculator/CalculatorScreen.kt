package com.oletob.rpncalc.feature.calculator

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oletob.rpncalc.R
import com.oletob.rpncalc.ui.theme.Accent
import com.oletob.rpncalc.ui.theme.AppBackground
import com.oletob.rpncalc.ui.theme.ButtonNumber
import com.oletob.rpncalc.ui.theme.ButtonSecondary
import com.oletob.rpncalc.ui.theme.Divider as DividerColor
import com.oletob.rpncalc.ui.theme.Muted
import com.oletob.rpncalc.ui.theme.OnAccent
import com.oletob.rpncalc.ui.theme.PanelBackground
import java.text.NumberFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorScreen(
    onNavigateToHistory: () -> Unit,
    onNavigateToAbout: () -> Unit,
    viewModel: CalculatorViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.errorEvent.collect { messageRes ->
            Toast.makeText(context, context.getString(messageRes), Toast.LENGTH_LONG).show()
        }
    }

    Scaffold(
        containerColor = AppBackground,
        topBar = {
            TopAppBar(
                title = { AppTitle() },
                actions = {
                    TextButton(onClick = onNavigateToHistory) {
                        Text(stringResource(R.string.history), color = Muted)
                    }
                    TextButton(onClick = onNavigateToAbout) {
                        Text(stringResource(R.string.about), color = Muted)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = AppBackground)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppBackground)
                .padding(padding)
                .padding(horizontal = 17.dp, vertical = 12.dp)
        ) {
            StackLedger(numbers = uiState.numbers, modifier = Modifier.weight(1f))
            Spacer(Modifier.height(16.dp))
            Keypad(
                onNumberClick = viewModel::onNumberClick,
                onClear = viewModel::onClear,
                onEnterClick = viewModel::onEnterClick,
                onDeleteClick = viewModel::onDeleteClick,
                onOperatorClick = viewModel::onOperatorClick,
                onSymbolClick = viewModel::onSymbolClick
            )
        }
    }
}

@Composable
private fun AppTitle() {
    val fullName = stringResource(R.string.app_name)
    val (first, rest) = remember(fullName) {
        val spaceIndex = fullName.indexOf(' ')
        if (spaceIndex == -1) fullName to "" else fullName.take(spaceIndex) to fullName.substring(spaceIndex + 1)
    }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = first, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        if (rest.isNotEmpty()) {
            Spacer(Modifier.width(6.dp))
            Text(text = rest, color = Accent, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        }
    }
}

@Composable
private fun StackLedger(numbers: List<String>, modifier: Modifier = Modifier) {
    val settled = remember(numbers) { numbers.dropLast(1) }
    val current = numbers.last()
    val displayFormat = remember {
        NumberFormat.getInstance().apply {
            minimumFractionDigits = 2
            maximumFractionDigits = 2
        }
    }
    val listState = rememberLazyListState()

    LaunchedEffect(settled.size) {
        if (settled.isNotEmpty()) listState.animateScrollToItem(settled.lastIndex)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(PanelBackground)
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier.weight(1f).fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            itemsIndexed(settled) { index, value ->
                val label = stringResource(R.string.stack_position_format, settled.size - index)
                LedgerRow(
                    label = label,
                    value = value.toDoubleOrNull()?.let(displayFormat::format) ?: value,
                    labelColor = Muted,
                    valueColor = Color.White,
                    valueFontSize = 17.sp
                )
            }
        }

        if (settled.isNotEmpty()) {
            HorizontalDivider(color = DividerColor)
        }

        LedgerRow(
            label = stringResource(R.string.current_entry_label),
            value = current,
            labelColor = Accent,
            valueColor = Color.White,
            valueFontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .background(Accent.copy(alpha = 0.05f))
                .padding(horizontal = 20.dp, vertical = 16.dp)
        )
    }
}

@Composable
private fun LedgerRow(
    label: String,
    value: String,
    labelColor: Color,
    valueColor: Color,
    valueFontSize: TextUnit,
    fontWeight: FontWeight = FontWeight.Normal,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = label, color = labelColor, fontSize = 15.sp)
        Text(text = value, color = valueColor, fontSize = valueFontSize, fontWeight = fontWeight, textAlign = TextAlign.End)
    }
}

@Composable
private fun Keypad(
    onNumberClick: (String) -> Unit,
    onClear: () -> Unit,
    onEnterClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onOperatorClick: (CalculatorViewModel.Operator) -> Unit,
    onSymbolClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        KeypadRow {
            CalcButton(stringResource(R.string.clear_btn), ButtonSecondary, Muted, Modifier.weight(1f), onClick = onClear)
            CalcButton(stringResource(R.string.seven), ButtonNumber, Color.White, Modifier.weight(1f)) { onNumberClick("7") }
            CalcButton(stringResource(R.string.eight), ButtonNumber, Color.White, Modifier.weight(1f)) { onNumberClick("8") }
            CalcButton(stringResource(R.string.nine), ButtonNumber, Color.White, Modifier.weight(1f)) { onNumberClick("9") }
        }
        KeypadRow {
            CalcButton(stringResource(R.string.delete), ButtonSecondary, Muted, Modifier.weight(1f), onClick = onDeleteClick)
            CalcButton(stringResource(R.string.four), ButtonNumber, Color.White, Modifier.weight(1f)) { onNumberClick("4") }
            CalcButton(stringResource(R.string.five), ButtonNumber, Color.White, Modifier.weight(1f)) { onNumberClick("5") }
            CalcButton(stringResource(R.string.six), ButtonNumber, Color.White, Modifier.weight(1f)) { onNumberClick("6") }
        }
        KeypadRow {
            CalcButton(stringResource(R.string.multiply_symbol), Accent, OnAccent, Modifier.weight(1f)) {
                onOperatorClick(CalculatorViewModel.Operator.MULTIPLY)
            }
            CalcButton(stringResource(R.string.one), ButtonNumber, Color.White, Modifier.weight(1f)) { onNumberClick("1") }
            CalcButton(stringResource(R.string.two), ButtonNumber, Color.White, Modifier.weight(1f)) { onNumberClick("2") }
            CalcButton(stringResource(R.string.three), ButtonNumber, Color.White, Modifier.weight(1f)) { onNumberClick("3") }
        }
        KeypadRow {
            CalcButton(stringResource(R.string.divide_symbol), Accent, OnAccent, Modifier.weight(1f)) {
                onOperatorClick(CalculatorViewModel.Operator.DIVIDE)
            }
            CalcButton(stringResource(R.string.zero), ButtonNumber, Color.White, Modifier.weight(1f)) { onNumberClick("0") }
            CalcButton(stringResource(R.string.dot), ButtonNumber, Color.White, Modifier.weight(1f)) { onNumberClick(".") }
            CalcButton(stringResource(R.string.plus_minus_symbol), ButtonNumber, Color.White, Modifier.weight(1f), onClick = onSymbolClick)
        }
        KeypadRow {
            CalcButton(
                text = stringResource(R.string.enter),
                backgroundColor = Accent,
                contentColor = OnAccent,
                modifier = Modifier.weight(2f),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                onClick = onEnterClick
            )
            CalcButton(stringResource(R.string.sum_symbol), Accent, OnAccent, Modifier.weight(1f)) {
                onOperatorClick(CalculatorViewModel.Operator.SUM)
            }
            CalcButton(stringResource(R.string.subtract_symbol), Accent, OnAccent, Modifier.weight(1f)) {
                onOperatorClick(CalculatorViewModel.Operator.SUBTRACT)
            }
        }
    }
}

@Composable
private fun KeypadRow(content: @Composable RowScope.() -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().height(64.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        content = content
    )
}

@Composable
private fun CalcButton(
    text: String,
    backgroundColor: Color,
    contentColor: Color,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 20.sp,
    fontWeight: FontWeight = FontWeight.Medium,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxHeight(),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor, contentColor = contentColor),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp),
        contentPadding = PaddingValues(4.dp)
    ) {
        Text(text = text, fontSize = fontSize, fontWeight = fontWeight)
    }
}
