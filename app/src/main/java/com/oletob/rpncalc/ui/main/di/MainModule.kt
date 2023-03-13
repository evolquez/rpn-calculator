package com.oletob.rpncalc.ui.main.di

import com.oletob.rpncalc.data.repository.MathOperationRepository
import com.oletob.rpncalc.di.ActivityScope
import com.oletob.rpncalc.ui.main.MainContract
import com.oletob.rpncalc.ui.main.MainPresenter
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope

@Module
class MainModule {

    @ActivityScope
    @Provides
    fun presenter(view: MainContract.View, mathOperationRepository: MathOperationRepository, scope: CoroutineScope): MainContract.Presenter {
        return MainPresenter(view, mathOperationRepository, scope)
    }
}