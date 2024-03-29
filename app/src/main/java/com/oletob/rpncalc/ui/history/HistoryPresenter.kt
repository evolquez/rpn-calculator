package com.oletob.rpncalc.ui.history

import androidx.lifecycle.MutableLiveData
import com.oletob.rpncalc.data.repository.MathOperationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class HistoryPresenter(
    private val view: HistoryContract.View,
    private val mathOperationRepository: MathOperationRepository,
    private val scope: CoroutineScope
): HistoryContract.Presenter {

    private val historyItems = ArrayList<BaseItem>()

    private val liveHistory: MutableLiveData<List<BaseItem>> by lazy {
        MutableLiveData<List<BaseItem>>()
    }

    override fun liveHistory() = liveHistory

    override fun init() {
        scope.launch {
            historyItems.clear()

            mathOperationRepository.getHistory().let {
                if(it.isNotEmpty()) {

                    it.forEach { operation ->
                        historyItems.add(HistoryRowItem(operation.statement, operation.result))
                    }
                }else {
                    historyItems.add(EmptyRowItem())
                }

                liveHistory.postValue(historyItems)
            }
        }
    }

    override fun onClickClear() {
        view.showClearConfirmation()
    }

    override fun clearHistory() {
        scope.launch {
            mathOperationRepository.clear()
            init()
        }
    }
    class HistoryRowItem(val expression: String, val result: String): BaseItem(ItemType.HistoryItem)
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