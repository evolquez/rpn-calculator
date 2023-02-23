package com.oletob.rpncalc.ui.history

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oletob.rpncalc.R
import com.oletob.rpncalc.databinding.ActivityHistoryBinding
import com.oletob.rpncalc.databinding.HistoryItemBinding
import com.oletob.rpncalc.databinding.NoHistoryItemBinding
import com.oletob.rpncalc.ui.common.BaseActivity

class HistoryActivity: BaseActivity(), HistoryContract.View {

    private lateinit var presenter: HistoryContract.Presenter
    private lateinit var binding: ActivityHistoryBinding
    private lateinit var adapter: HistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHistoryBinding.inflate(layoutInflater)

        setContentView(binding.root)

        adapter = HistoryAdapter()

        with(binding) {
            recyclerView.setHasFixedSize(false)
            recyclerView.layoutManager = LinearLayoutManager(this@HistoryActivity)
            recyclerView.adapter = adapter
        }

        setActionBar(R.string.history, true)

        presenter = HistoryPresenter(this)
        presenter.init()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            onBackPressedDispatcher.onBackPressed()
        }
        when(item.itemId){
            android.R.id.home -> onBackPressedDispatcher.onBackPressed()
            R.id.item_clear -> showToast(getString(R.string.history))
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.clear_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun showHistory(items: List<HistoryPresenter.BaseItem>) {
        adapter.items = items
        adapter.notifyDataSetChanged()
    }

    inner class HistoryAdapter: RecyclerView.Adapter<BaseViewHolder<in HistoryPresenter.BaseItem>>() {
        private val inflater = LayoutInflater.from(this@HistoryActivity)
        var items: List<HistoryPresenter.BaseItem> = emptyList()

        @Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): BaseViewHolder<in HistoryPresenter.BaseItem> {
            return when(HistoryPresenter.ItemType.fromValue(viewType)) {
                HistoryPresenter.ItemType.HistoryItem -> HistoryItemViewHolder(HistoryItemBinding.inflate(inflater, parent, false))
                HistoryPresenter.ItemType.EmptyItem -> EmptyItemViewHolder(NoHistoryItemBinding.inflate(inflater, parent, false))
                else -> throw IllegalArgumentException("Unknown viewType $viewType")
            } as BaseViewHolder<in HistoryPresenter.BaseItem>
        }

        override fun onBindViewHolder(
            holder: BaseViewHolder<in HistoryPresenter.BaseItem>,
            position: Int
        ) {
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