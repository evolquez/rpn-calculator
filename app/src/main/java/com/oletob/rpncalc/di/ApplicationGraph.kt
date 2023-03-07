package com.oletob.rpncalc.di

import android.content.Context
import com.oletob.rpncalc.ui.about.di.AboutComponent
import com.oletob.rpncalc.ui.history.di.HistoryComponent
import com.oletob.rpncalc.ui.main.di.MainComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, SubcomponentsModule::class])
interface ApplicationGraph {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationGraph
    }

    fun mainComponent(): MainComponent.Factory
    fun aboutComponent(): AboutComponent.Factory
    fun historyComponent(): HistoryComponent.Factory
}