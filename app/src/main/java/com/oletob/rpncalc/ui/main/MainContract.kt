package com.oletob.rpncalc.ui.main

interface MainContract {

    interface View {
        fun setPanelText(text: String)
        fun getPanelText(): String
        fun appendToPanelText(text: String)
    }

    interface Presenter {
        fun onClickHistory()
        fun onClickAbout()

        fun onClickNumber(number: Int)

        fun onClickClear()
        fun onClickEnter()
        fun onClickDelete()

        fun onClickSum()
        fun onClickDivide()
        fun onClickMultiply()
        fun onClickSubtract()
    }
}