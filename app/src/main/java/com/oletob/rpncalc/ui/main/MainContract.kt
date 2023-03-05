package com.oletob.rpncalc.ui.main

import com.oletob.rpncalc.ui.base.BaseView

interface MainContract {

    interface View: BaseView {
        fun setNumbers(numbers: MutableList<String>)
    }

    interface Presenter {

        fun onClickNumber(input: String)
        fun onClickClear()
        fun onClickEnter()
        fun onClickDelete()
        fun onClickOperator(operator: MainPresenter.Operator)
        fun onClickSymbol()
    }
}