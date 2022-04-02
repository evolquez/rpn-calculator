package com.oletob.rpncalc.ui.main

import com.oletob.rpncalc.R
import com.oletob.rpncalc.model.RpnModel

class MainPresenter(private val view: MainActivity): MainContract.Presenter {

    override fun onClickHistory() {
        TODO("Not yet implemented")
    }

    override fun onClickAbout() {
        TODO("Not yet implemented")
    }

    override fun onClickNumber(number: Int) {
        if(view.getPanelText().length == 1 && view.getPanelText().toDouble() == 0.0){
            view.setPanelText(number.toString())
        }else{
            view.appendToPanelText(number.toString())
        }
    }

    override fun onClickClear() {
        view.setPanelText(view.getString(R.string.zero))
    }

    override fun onClickEnter() {

        if(view.getPanelText().last().toString().toDouble() != 0.0){
            view.setPanelText(RpnModel.formatInput(view.getPanelText()))
        }
    }

    override fun onClickDelete() {

        val newInput = view.getPanelText().substring(0, view.getPanelText().length - 1)

        if(newInput.isNotEmpty() && newInput.last() == '\n'){
            newInput.let {
                it.substring(0, it.length - 1)
            }
        }

        view.setPanelText(newInput.ifEmpty { view.getString(R.string.zero) })
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