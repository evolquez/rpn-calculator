package com.oletob.rpncalc.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import com.oletob.rpncalc.R
import com.oletob.rpncalc.ui.about.AboutActivity
import com.oletob.rpncalc.ui.common.BaseActivity
import com.oletob.rpncalc.ui.history.HistoryActivity

class MainActivity: BaseActivity(), MainContract.View {

    private lateinit var panelTextView: TextView
    private lateinit var presenter: MainPresenter

    //Numbered buttons
    private val numberButtons = listOf(
        R.id.button_0,
        R.id.button_1,
        R.id.button_2,
        R.id.button_3,
        R.id.button_4,
        R.id.button_5,
        R.id.button_6,
        R.id.button_7,
        R.id.button_8,
        R.id.button_9
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainPresenter(this)

        panelTextView = findViewById(R.id.text_view_panel)

        // Buttons control
        findViewById<Button>(R.id.button_clear).setOnClickListener{presenter.onClickClear()}
        findViewById<Button>(R.id.button_enter).setOnClickListener{presenter.onClickEnter()}
        findViewById<Button>(R.id.button_delete).setOnClickListener{presenter.onClickDelete()}

        numberButtons.forEach{
            val button = findViewById<Button>(it)
            button.setOnClickListener{presenter.onClickNumber(button.text.toString().toInt())}
        }

        // Operators
        findViewById<Button>(R.id.button_divide).setOnClickListener{presenter.onClickOperator(MainPresenter.Operator.DIVIDE)}
        findViewById<Button>(R.id.button_multiply).setOnClickListener{presenter.onClickOperator(MainPresenter.Operator.MULTIPLY)}
        findViewById<Button>(R.id.button_subtract).setOnClickListener{presenter.onClickOperator(MainPresenter.Operator.SUBTRACT)}
        findViewById<Button>(R.id.button_sum).setOnClickListener{presenter.onClickOperator(MainPresenter.Operator.SUM)}
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.settings_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.button_history -> startActivity(HistoryActivity.createIntent(this))
            R.id.button_about -> startActivity(AboutActivity.createIntent(this))
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun getPanelText() = panelTextView.text.toString()

    override fun setPanelText(text: String) {
        panelTextView.text = text
    }

    override fun appendToPanelText(text: String) {
        panelTextView.append(text)
    }
}