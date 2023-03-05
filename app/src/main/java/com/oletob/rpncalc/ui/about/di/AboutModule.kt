package com.oletob.rpncalc.ui.about.di

import com.oletob.rpncalc.di.ActivityScope
import com.oletob.rpncalc.ui.about.AboutContract
import com.oletob.rpncalc.ui.about.AboutPresenter
import dagger.Module
import dagger.Provides

@Module
class AboutModule {

    @ActivityScope
    @Provides
    fun presenter(view: AboutContract.View): AboutContract.Presenter {
        return AboutPresenter(view)
    }
}