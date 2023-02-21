package com.oletob.rpncalc.ui.main

class MainPresenter(private val view: MainActivity, private val numbers: MutableList<String>): MainContract.Presenter {

    override fun onClickNumber(input: String) {
        val last = numbers.last()

        val newInput = if(last.toDouble() == 0.0){
            if(input == "." || last.last() == '.') last+input else input
        } else last+input;

        numbers[numbers.lastIndex] = newInput
        view.setNumbers(numbers)
    }

    override fun onClickClear() {
        numbers.clear()
        numbers.add("0")
        view.setNumbers(numbers)
    }

    override fun onClickEnter() {
        if(numbers.last().toDouble() == 0.0) return

        numbers.add("0")

        view.setNumbers(numbers)
    }

    override fun onClickDelete() {

        if(numbers.last().toDouble() != 0.0){
            val last = numbers.last().dropLast(1)
            numbers[numbers.lastIndex] = last
            if(last.isEmpty())
                numbers.removeLast()
        }

        if(numbers.isEmpty())
            numbers.add("0")

        view.setNumbers(numbers)
    }

    override fun onClickOperator(operator: Operator) {
        if(numbers.size > 1){

            val num1 = numbers.removeLast().toDouble()

            val num2 = numbers.last().toDouble()
            numbers.removeLast()

            val result = when(operator){
                Operator.DIVIDE -> num2.div(num1)
                Operator.MULTIPLY -> num2.times(num1)
                Operator.SUBTRACT -> num2.minus(num1)
                Operator.SUM -> num2.plus(num1)
            }.toDouble().toString()

            numbers.add(result)
            view.setNumbers(numbers)
        }
    }

    override fun onClickSymbol() {
        val lastIndex = numbers.lastIndex
        val last = numbers[lastIndex].toDouble()

        numbers[lastIndex] = if(last != 0.0) (last * -1).toString() else last.toString()
        view.setNumbers(numbers)
    }

    enum class Operator{
        SUM,
        SUBTRACT,
        MULTIPLY,
        DIVIDE
    }
}