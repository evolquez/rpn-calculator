package com.oletob.rpncalc.model

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
     * Format numbers to show on history
     * @param number
     * @return String
     */
    fun applyFormat(number: Double): String = DecimalFormat("#,###.#").format(number)
}