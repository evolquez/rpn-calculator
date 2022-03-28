package com.oletob.rpncalc.ui.main

import com.oletob.rpncalc.R

class MainPresenter(private val view: MainActivity): MainContract.Presenter {

    override fun onClickHistory() {
        TODO("Not yet implemented")
    }

    override fun onClickAbout() {
        TODO("Not yet implemented")
    }

    override fun onClickNumber(number: Int) {
        if(view.getPanelText().length == 1 && view.getPanelText().toInt() == 0){
            view.setPanelText(number.toString())
        }else{
            view.appendToPanelText(number.toString())
        }
    }

    override fun onClickClear() {
        view.setPanelText(view.getString(R.string.zero))
    }

    override fun onClickEnter() {
        TODO("Not yet implemented")
    }

    override fun onClickDelete() {
        TODO("Not yet implemented")
    }

    override fun onClickSum() {
        TODO("Not yet implemented")
    }

    override fun onClickDivide() {
        TODO("Not yet implemented")
    }

    override fun onClickMultiply() {
        TODO("Not yet implemented")
    }

    override fun onClickSubtract() {
        TODO("Not yet implemented")
    }
}