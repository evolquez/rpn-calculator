package com.oletob.rpncalc.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.oletob.rpncalc.R
import com.oletob.rpncalc.RpnApplication
import com.oletob.rpncalc.databinding.ActivityMainBinding
import com.oletob.rpncalc.ui.about.AboutActivity
import com.oletob.rpncalc.ui.base.BaseActivity
import com.oletob.rpncalc.ui.history.HistoryActivity
import javax.inject.Inject

class MainActivity: BaseActivity(), MainContract.View {

    @Inject
    lateinit var presenter: MainContract.Presenter

    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        (applicationContext as RpnApplication)
            .appGraph
            .mainComponent()
            .create(this)
            .inject(this)

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

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
            R.id.item_history -> startActivity(HistoryActivity.createIntent(this))
            R.id.item_about -> startActivity(AboutActivity.createIntent(this))
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun setNumbers(numbers: MutableList<String>) {
        adapter.numbers = numbers
        adapter.notifyDataSetChanged()
    }
}