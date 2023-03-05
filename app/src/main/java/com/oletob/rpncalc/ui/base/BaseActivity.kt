package com.oletob.rpncalc.ui.base

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity: AppCompatActivity(), BaseView {

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun setActionBar(titleRes: Int, displayHomeAsUpEnabled: Boolean) {
        supportActionBar?.title = getString(titleRes)
        supportActionBar?.setDisplayHomeAsUpEnabled(displayHomeAsUpEnabled)
    }
}