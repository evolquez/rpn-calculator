package com.oletob.rpncalc.ui.history

import com.oletob.rpncalc.data.repository.MathOperationRepository

class HistoryPresenter(
    private val view: HistoryContract.View,
    private val mathOperationRepository: MathOperationRepository
): HistoryContract.Presenter {

    private val historyItems = ArrayList<BaseItem>()

    override fun init() {

        val history = mathOperationRepository.getHistory()

        if(history.isNotEmpty()){
            history.forEach {
                historyItems.add(HistoryRowItem(it.statement, it.result))
            }
        }else{
            historyItems.add(EmptyRowItem())
        }

        view.showHistory(historyItems)
    }

    override fun onClickClear() {
        view.showClearConfirmation()
    }

    override fun clearHistory() {
        mathOperationRepository.clear()
        historyItems.apply {
            clear()
            add(EmptyRowItem())
        }.also {  view.showHistory(it) }
    }
    class HistoryRowItem(val expression: String, val result: Double): BaseItem(ItemType.HistoryItem)
    class EmptyRowItem: BaseItem(ItemType.EmptyItem)

    enum class ItemType(val value: Int){
        HistoryItem(0),
        EmptyItem(1);

        companion object {
            fun fromValue(value: Int): ItemType? = values().firstOrNull { value == it.value }
        }
    }

    abstract class BaseItem(val itemType: ItemType)
}