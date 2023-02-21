package com.oletob.rpncalc.model

import com.oletob.rpncalc.ui.main.MainPresenter
import java.lang.StringBuilder
import java.text.DecimalFormat

/**
 * This class includes some methods to support the logic of the RPN calc.
 * @author Algenis Volquez - evolquez@gmail.com
 *
 * https://github.com/evolquez/
 */
object RpnModel {
    const val RPN_HISTORY_KEY = "RPN_HISTORY"

    private val mathSymbols = arrayOf("/", "x", "-", "+")
    private var symbolPosition = -1

    /**
     * Format the input on calculator
     * @param input
     * @return String
     */
    fun formatInput(input: String): String {

         var newInput = input

        if(input.last() == '\n')
           newInput = input.substring(0, input.length - 1)

        val inputList = newInput.split("\n")

        val inputBuilder = StringBuilder()
        var number: Double
        var count = 0

        for(value: String in inputList){

            number = value.toDouble()
            count += 1
            inputBuilder.append(number)

            if(count < inputList.size)
                inputBuilder.append("\n")
        }

        return inputBuilder.append("\n0").toString()
    }

    /**
     * Process the operation based on operator
     * @param input
     * @param operator
     * @return String
     **/
    fun processOperation(input: String, operator: MainPresenter.Operator): String? {
        val inputList = input.split("\n")

        val listSize = inputList.size

        if(listSize > 1){

            val op1 = inputList[listSize-1].toDouble()
            val op2 = inputList[listSize-2].toDouble()

            return when(operator){
                MainPresenter.Operator.DIVIDE -> (op1 / op2)
                MainPresenter.Operator.MULTIPLY -> (op1 * op2)
                MainPresenter.Operator.SUBTRACT -> (op1 - op2)
                MainPresenter.Operator.SUM -> (op1 + op2)
            }.toString()
        }

        return null
    }

    /**
     * Format numbers to show on history
     * @param number
     * @return String
     */
    fun applyFormat(number: Double): String = DecimalFormat("#,###.#").format(number)
}