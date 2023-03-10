package com.oletob.rpncalc.ui.main

import androidx.lifecycle.LiveData
import com.oletob.rpncalc.ui.base.BaseView

interface MainContract {

    interface View: BaseView

    interface Presenter {
        fun liveNumbers(): LiveData<List<String>>
        fun onClickNumber(input: String)
        fun onClickClear()
        fun onClickEnter()
        fun onClickDelete()
        fun onClickOperator(operator: MainPresenter.Operator)
        fun onClickSymbol()
    }
}