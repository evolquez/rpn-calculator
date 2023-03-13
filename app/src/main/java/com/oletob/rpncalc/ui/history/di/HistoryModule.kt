package com.oletob.rpncalc.ui.history.di

import com.oletob.rpncalc.data.repository.MathOperationRepository
import com.oletob.rpncalc.di.ActivityScope
import com.oletob.rpncalc.ui.history.HistoryContract
import com.oletob.rpncalc.ui.history.HistoryPresenter
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope

@Module
class HistoryModule {

    @ActivityScope
    @Provides
    fun presenter(view: HistoryContract.View, mathOperationRepository: MathOperationRepository, scope: CoroutineScope): HistoryContract.Presenter {
        return HistoryPresenter(view, mathOperationRepository, scope)
    }
}