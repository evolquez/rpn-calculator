package com.oletob.rpncalc.ui.history

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.oletob.rpncalc.R
import com.oletob.rpncalc.ui.common.BaseActivity

class HistoryActivity: BaseActivity(), HistoryContract.View {

    private lateinit var presenter: HistoryContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_history)

        setActionBar(R.string.history, true)

        presenter = HistoryPresenter(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) onBackPressed()
        when(item.itemId){
            android.R.id.home -> onBackPressed()
            R.id.clear_button -> showToast(getString(R.string.history))
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.clear_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    companion object {
        fun createIntent(context: Context) = Intent(context, HistoryActivity::class.java)
    }
}