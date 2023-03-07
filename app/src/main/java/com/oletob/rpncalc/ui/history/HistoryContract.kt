package com.oletob.rpncalc.ui.history

import com.oletob.rpncalc.ui.base.BaseView

interface HistoryContract {

    interface View: BaseView {
        fun showHistory(items: List<HistoryPresenter.BaseItem>)
        fun showClearConfirmation()
    }

    interface Presenter {
        fun init()
        fun onClickClear()
        fun clearHistory()
    }
}