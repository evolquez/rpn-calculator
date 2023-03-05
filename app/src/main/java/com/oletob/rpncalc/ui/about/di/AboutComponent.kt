package com.oletob.rpncalc.ui.about.di

import com.oletob.rpncalc.di.ActivityScope
import com.oletob.rpncalc.ui.about.AboutActivity
import com.oletob.rpncalc.ui.about.AboutContract
import dagger.BindsInstance
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [AboutModule::class])
interface AboutComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance view: AboutContract.View): AboutComponent
    }

    fun inject(activity: AboutActivity)
}