package com.oletob.rpncalc.ui.history

class HistoryPresenter(private val view: HistoryContract.View): HistoryContract.Presenter {

    override fun init() {
        val newItems = ArrayList<BaseItem>()

        newItems.add(EmptyRowItem())

        view.showHistory(newItems)
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