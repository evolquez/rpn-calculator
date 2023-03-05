package com.oletob.rpncalc.di

import com.oletob.rpncalc.ui.about.di.AboutComponent
import com.oletob.rpncalc.ui.history.di.HistoryComponent
import com.oletob.rpncalc.ui.main.di.MainComponent
import dagger.Component

@Component(modules = [SubcomponentsModule::class])
interface ApplicationGraph {

    @Component.Factory
    interface Factory {
        fun create(): ApplicationGraph
    }

    fun mainComponent(): MainComponent.Factory
    fun aboutComponent(): AboutComponent.Factory
    fun historyComponent(): HistoryComponent.Factory
}