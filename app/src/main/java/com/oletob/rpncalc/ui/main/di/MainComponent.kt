package com.oletob.rpncalc.ui.main.di

import com.oletob.rpncalc.di.ActivityScope
import com.oletob.rpncalc.ui.main.MainActivity
import com.oletob.rpncalc.ui.main.MainContract
import dagger.BindsInstance
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [MainModule::class])
interface MainComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance view: MainContract.View): MainComponent
    }

    fun inject(activity: MainActivity)
}