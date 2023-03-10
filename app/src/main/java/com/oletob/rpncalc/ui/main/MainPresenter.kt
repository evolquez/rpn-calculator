package com.oletob.rpncalc.ui.main

import androidx.lifecycle.MutableLiveData
import com.oletob.rpncalc.R
import com.oletob.rpncalc.data.model.entity.MathOperation
import com.oletob.rpncalc.data.repository.MathOperationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MainPresenter(
    private val view: MainContract.View,
    private val mathOperationRepository: MathOperationRepository
): MainContract.Presenter {

    private val numbers = mutableListOf("0")
    private val presenterScope = CoroutineScope(SupervisorJob())

     private val liveNumbers: MutableLiveData<List<String>> by lazy {
        MutableLiveData<List<String>>()
    }

    override fun liveNumbers() = liveNumbers

    override fun onClickNumber(input: String) {
        with(numbers) {
            val newInput = if(last().toDouble() == 0.0){
                if(input == "." || last().last() == '.') last()+input else input
            } else{
                last()+input
            }

            this[lastIndex] = newInput
            liveNumbers.value = numbers
        }
    }

    override fun onClickClear() {
        numbers.apply {
            clear()
            add("0")
        }.also {  liveNumbers.value = it }
    }

    override fun onClickEnter() {
        if(numbers.last().toDouble() == 0.0) return

        numbers.apply {
            add("0")
        }.also {  liveNumbers.value = it }
    }

    override fun onClickDelete() {
        with(numbers) {
            if(last().toDouble() != 0.0){
                val last = last().dropLast(1)
                this[lastIndex] = last
                if(last.isEmpty()) removeLast()
            }
            if(isEmpty()) add("0")
            liveNumbers.value = numbers
        }
    }

    override fun onClickOperator(operator: Operator) {
        with(numbers) {
            if(size > 1){
                if(last().toDouble() == 0.0 && operator == Operator.DIVIDE){
                    view.showToast(view.getString(R.string.divide_by_zero_message))
                    return
                }
                val num1 = removeLast().toDouble()
                val num2 = last().toDouble()

                removeLast()

                val result = when(operator){
                    Operator.DIVIDE -> num2.div(num1)
                    Operator.MULTIPLY -> num2.times(num1)
                    Operator.SUBTRACT -> num2.minus(num1)
                    Operator.SUM -> num2.plus(num1)
                }.toDouble()

                val statement: String = view
                    .getString(
                        R.string.math_operation_format,
                        num2,
                        view.getString(operator.symbol),
                        num1)
                presenterScope.launch {
                    mathOperationRepository.addOperation(MathOperation(0, statement, result))
                    add(result.toString())
                    liveNumbers.postValue(this@with)
                }
            }
        }
    }

    override fun onClickSymbol() {
        with(numbers) {
            val last = this[lastIndex].toDouble()
            this[lastIndex] = if(last != 0.0) (last * -1).toString() else last.toString()
            liveNumbers.value = this
        }
    }

    enum class Operator(val symbol: Int){
        SUM(R.string.sum_symbol),
        SUBTRACT(R.string.subtract_symbol),
        MULTIPLY(R.string.multiply_symbol),
        DIVIDE(R.string.divide_symbol)
    }
}