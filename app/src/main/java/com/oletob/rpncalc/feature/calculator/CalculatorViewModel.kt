package com.oletob.rpncalc.feature.calculator

import android.content.Context
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oletob.rpncalc.R
import com.oletob.rpncalc.data.local.MathOperation
import com.oletob.rpncalc.data.repository.MathOperationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.NumberFormat
import javax.inject.Inject

data class CalculatorUiState(val numbers: List<String> = listOf("0"))

@HiltViewModel
class CalculatorViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val mathOperationRepository: MathOperationRepository
) : ViewModel() {

    private val numberFormat: NumberFormat by lazy { NumberFormat.getInstance() }

    private val _uiState = MutableStateFlow(CalculatorUiState())
    val uiState: StateFlow<CalculatorUiState> = _uiState.asStateFlow()

    private val _errorEvent = Channel<Int>(Channel.BUFFERED)
    val errorEvent = _errorEvent.receiveAsFlow()

    fun onNumberClick(input: String) = updateNumbers { numbers ->
        val last = numbers.last()
        val newLast = if (last.toDouble() == 0.0) {
            if (input == "." || last.last() == '.') last + input else input
        } else {
            last + input
        }
        numbers.dropLast(1) + newLast
    }

    fun onClear() {
        _uiState.update { CalculatorUiState() }
    }

    fun onEnterClick() = updateNumbers { numbers ->
        if (numbers.last().toDouble() == 0.0) numbers else numbers + "0"
    }

    fun onDeleteClick() = updateNumbers { numbers ->
        val mutable = numbers.toMutableList()
        if (mutable.last().toDouble() != 0.0) {
            val trimmed = mutable.last().dropLast(1)
            mutable[mutable.lastIndex] = trimmed
            if (trimmed.isEmpty()) mutable.removeAt(mutable.lastIndex)
        }
        if (mutable.isEmpty()) mutable.add("0")
        mutable
    }

    fun onOperatorClick(operator: Operator) {
        val numbers = _uiState.value.numbers
        if (numbers.size <= 1) return

        val num1 = numbers.last().toDouble()
        val num2 = numbers[numbers.lastIndex - 1].toDouble()

        if (num1 == 0.0 && operator == Operator.DIVIDE) {
            _errorEvent.trySend(R.string.divide_by_zero_message)
            return
        }

        val result = when (operator) {
            Operator.DIVIDE -> num2 / num1
            Operator.MULTIPLY -> num2 * num1
            Operator.SUBTRACT -> num2 - num1
            Operator.SUM -> num2 + num1
        }

        val statement = context.getString(
            R.string.math_operation_format,
            numberFormat.format(num2),
            context.getString(operator.symbolRes),
            numberFormat.format(num1)
        )

        viewModelScope.launch {
            mathOperationRepository.addOperation(
                MathOperation(id = 0, statement = statement, result = numberFormat.format(result))
            )
        }

        _uiState.update { it.copy(numbers = numbers.dropLast(2) + result.toString()) }
    }

    fun onSymbolClick() = updateNumbers { numbers ->
        val last = numbers.last().toDouble()
        val newLast = if (last != 0.0) (last * -1).toString() else last.toString()
        numbers.dropLast(1) + newLast
    }

    private inline fun updateNumbers(transform: (List<String>) -> List<String>) {
        _uiState.update { it.copy(numbers = transform(it.numbers)) }
    }

    enum class Operator(@StringRes val symbolRes: Int) {
        SUM(R.string.sum_symbol),
        SUBTRACT(R.string.subtract_symbol),
        MULTIPLY(R.string.multiply_symbol),
        DIVIDE(R.string.divide_symbol)
    }
}
