package com.oletob.rpncalc.ui.history

import androidx.lifecycle.MutableLiveData
import com.oletob.rpncalc.ui.base.BaseView

interface HistoryContract {

    interface View: BaseView {
        fun showClearConfirmation()
    }

    interface Presenter {
        fun liveHistory(): MutableLiveData<List<HistoryPresenter.BaseItem>>
        fun init()
        fun onClickClear()
        fun clearHistory()
    }
}