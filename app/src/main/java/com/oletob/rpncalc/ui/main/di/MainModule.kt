package com.oletob.rpncalc.ui.main.di

import com.oletob.rpncalc.di.ActivityScope
import com.oletob.rpncalc.ui.main.MainContract
import com.oletob.rpncalc.ui.main.MainPresenter
import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @ActivityScope
    @Provides
    fun presenter(view: MainContract.View): MainContract.Presenter {
        return MainPresenter(view)
    }
}