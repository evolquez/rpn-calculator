package com.oletob.rpncalc.ui.history

import com.oletob.rpncalc.ui.common.BaseView

interface HistoryContract {

    interface View: BaseView {
        fun showHistory(items: List<HistoryPresenter.BaseItem>)
    }

    interface Presenter {
        fun init()
    }
}