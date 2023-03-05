package com.oletob.rpncalc.ui.history.di

import com.oletob.rpncalc.di.ActivityScope
import com.oletob.rpncalc.ui.history.HistoryActivity
import com.oletob.rpncalc.ui.history.HistoryContract
import dagger.BindsInstance
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [HistoryModule::class])
interface HistoryComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance view: HistoryContract.View): HistoryComponent
    }

    fun inject(activity: HistoryActivity)
}