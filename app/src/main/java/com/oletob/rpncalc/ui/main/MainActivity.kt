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

    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: MainAdapter

    private val numbers = mutableListOf("0")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        presenter = MainPresenter(this, numbers)

        adapter = MainAdapter(this)

        binding.listViewPanel.adapter = adapter

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
                button.setOnClickListener{presenter.onClickNumber(button.text.toString())}
            }

            buttonDot.setOnClickListener{presenter.onClickNumber(buttonDot.text.toString())}

            // Operators
            buttonDivide.setOnClickListener{presenter.onClickOperator(MainPresenter.Operator.DIVIDE)}
            buttonMultiply.setOnClickListener{presenter.onClickOperator(MainPresenter.Operator.MULTIPLY)}
            buttonSubtract.setOnClickListener{presenter.onClickOperator(MainPresenter.Operator.SUBTRACT)}
            buttonSum.setOnClickListener{presenter.onClickOperator(MainPresenter.Operator.SUM)}

            buttonSymbols.setOnClickListener{presenter.onClickSymbol()}
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


    override fun setNumbers(numbers: MutableList<String>) {
        adapter.numbers = numbers
        adapter.notifyDataSetChanged()
    }
}