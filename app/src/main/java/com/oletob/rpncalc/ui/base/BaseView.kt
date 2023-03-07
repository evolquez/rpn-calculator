package com.oletob.rpncalc.ui.base

import androidx.annotation.StringRes

interface BaseView {

    fun getString(@StringRes resId: Int, vararg args: Any?): String
    fun showToast(message: String)
    fun setActionBar(@StringRes titleRes: Int, displayHomeAsUpEnabled: Boolean)
}