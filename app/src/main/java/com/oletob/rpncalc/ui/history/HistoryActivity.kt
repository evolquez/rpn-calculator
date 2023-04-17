package com.oletob.rpncalc.ui.history

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oletob.rpncalc.R
import com.oletob.rpncalc.RpnApplication
import com.oletob.rpncalc.databinding.ActivityHistoryBinding
import com.oletob.rpncalc.databinding.HistoryItemBinding
import com.oletob.rpncalc.databinding.NoHistoryItemBinding
import com.oletob.rpncalc.ui.common.BaseActivity
import javax.inject.Inject

class HistoryActivity: BaseActivity(), HistoryContract.View {

    @Inject
    lateinit var presenter: HistoryContract.Presenter

    private lateinit var binding: ActivityHistoryBinding
    private lateinit var historyAdapter: HistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        (applicationContext as RpnApplication)
            .appGraph
            .historyComponent()
            .create(this)
            .inject(this)

        super.onCreate(savedInstanceState)

        binding = ActivityHistoryBinding.inflate(layoutInflater)

        setContentView(binding.root)

        historyAdapter = HistoryAdapter()

        with(binding.recyclerView) {
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(this@HistoryActivity)
            addItemDecoration(DividerItemDecoration(this@HistoryActivity, LinearLayoutManager.VERTICAL))
            adapter = historyAdapter
        }

        presenter.liveHistory().observe(this) {items ->
            items?.let {
                with(historyAdapter) {
                    this.items = items
                    notifyDataSetChanged()
                }
                binding.recyclerView.scrollToPosition(items.size - 1)
            }
        }

        setActionBar(R.string.history, true)

        presenter.init()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            onBackPressedDispatcher.onBackPressed()
        }
        when(item.itemId){
            android.R.id.home -> onBackPressedDispatcher.onBackPressed()
            R.id.item_clear -> presenter.onClickClear()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.clear_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun showClearConfirmation() {
        AlertDialog.Builder(this)
            .setMessage(R.string.clear_history_message)
            .setPositiveButton(R.string.yes) {_, _ -> presenter.clearHistory()}
            .setNegativeButton(R.string.no, null)
            .show()
    }

    inner class HistoryAdapter: RecyclerView.Adapter<BaseViewHolder<in HistoryPresenter.BaseItem>>() {

        var items: List<HistoryPresenter.BaseItem> = emptyList()

        @Suppress("UNCHECKED_CAST")
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): BaseViewHolder<in HistoryPresenter.BaseItem> {
            val inflater = LayoutInflater.from(parent.context)

            return when(HistoryPresenter.ItemType.fromValue(viewType)) {
                HistoryPresenter.ItemType.HistoryItem -> HistoryItemViewHolder(HistoryItemBinding.inflate(inflater, parent, false))
                HistoryPresenter.ItemType.EmptyItem -> EmptyItemViewHolder(NoHistoryItemBinding.inflate(inflater, parent, false))
                else -> throw IllegalArgumentException("Unknown viewType $viewType")
            } as BaseViewHolder<in HistoryPresenter.BaseItem>
        }

        override fun onBindViewHolder(holder: BaseViewHolder<in HistoryPresenter.BaseItem>, position: Int) {
            holder.bind(items[position])
        }

        override fun getItemViewType(position: Int) = items[position].itemType.value

        override fun getItemCount() = items.size
    }

    abstract class BaseViewHolder<ItemType: HistoryPresenter.BaseItem>(view: View): RecyclerView.ViewHolder(view) {
        abstract fun bind(itemType: ItemType)
    }

    inner class HistoryItemViewHolder(private val binding: HistoryItemBinding): BaseViewHolder<HistoryPresenter.HistoryRowItem>(binding.root){
        override fun bind(itemType: HistoryPresenter.HistoryRowItem) {
            with(binding) {
                textViewOperation.text = itemType.expression
                textViewResult.text = itemType.result.toString()
            }
        }
    }
    inner class EmptyItemViewHolder(binding: NoHistoryItemBinding): BaseViewHolder<HistoryPresenter.EmptyRowItem>(binding.root) {
        override fun bind(itemType: HistoryPresenter.EmptyRowItem) {}
    }

    companion object {
        fun createIntent(context: Context) = Intent(context, HistoryActivity::class.java)
    }
}