package com.oletob.rpncalc.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.oletob.rpncalc.R
import com.oletob.rpncalc.ui.HistoryActivity
import com.oletob.rpncalc.ui.about.AboutActivity

class MainActivity: AppCompatActivity(), MainContract.View {

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

        findViewById<Button>(R.id.button_clear).setOnClickListener{presenter.onClickClear()}
        findViewById<Button>(R.id.button_enter).setOnClickListener{presenter.onClickEnter()}
        findViewById<Button>(R.id.button_delete).setOnClickListener{presenter.onClickDelete()}

        numberButtons.forEach{
            val button = findViewById<Button>(it)
            button.setOnClickListener{presenter.onClickNumber(button.text.toString().toInt())}
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.settings_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.button_history -> startActivity(Intent(this, HistoryActivity::class.java))
            R.id.button_about -> startActivity(Intent(this, AboutActivity::class.java))
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