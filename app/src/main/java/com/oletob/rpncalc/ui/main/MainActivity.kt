package com.oletob.rpncalc.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import com.oletob.rpncalc.R
import com.oletob.rpncalc.databinding.ActivityMainBinding
import com.oletob.rpncalc.ui.about.AboutActivity
import com.oletob.rpncalc.ui.common.BaseActivity
import com.oletob.rpncalc.ui.history.HistoryActivity

class MainActivity: BaseActivity(), MainContract.View {

    private lateinit var presenter: MainPresenter

    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        presenter = MainPresenter(this)

        initClickListeners()
    }

    private fun initClickListeners() {

        //Numbered buttons
        val numberButtons = listOf(binding.button0, binding.button1, binding.button2, binding.button3, binding.button4, binding.button5, binding.button6, binding.button7, binding.button8, binding.button9)

        with(binding){

            // Buttons control
            buttonClear.setOnClickListener{presenter.onClickClear()}
            buttonEnter.setOnClickListener{presenter.onClickEnter()}
            buttonDelete.setOnClickListener{presenter.onClickDelete()}

            numberButtons.forEach {button ->
                button.setOnClickListener{presenter.onClickNumber(button.text.toString().toInt())}
            }

            // Operators
            buttonDivide.setOnClickListener{presenter.onClickOperator(MainPresenter.Operator.DIVIDE)}
            buttonMultiply.setOnClickListener{presenter.onClickOperator(MainPresenter.Operator.MULTIPLY)}
            buttonSubtract.setOnClickListener{presenter.onClickOperator(MainPresenter.Operator.SUBTRACT)}
            buttonSum.setOnClickListener{presenter.onClickOperator(MainPresenter.Operator.SUM)}
        }
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

    override fun getPanelText() = binding.textViewPanel.text.toString()

    override fun setPanelText(text: String) {
        binding.textViewPanel.text = text
    }

    override fun appendToPanelText(text: String) {
        binding.textViewPanel.append(text)
    }
}