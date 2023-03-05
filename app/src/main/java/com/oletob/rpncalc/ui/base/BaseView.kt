package com.oletob.rpncalc.ui.base

import androidx.annotation.StringRes

interface BaseView {

    fun showToast(message: String)
    fun setActionBar(@StringRes titleRes: Int, displayHomeAsUpEnabled: Boolean)
}