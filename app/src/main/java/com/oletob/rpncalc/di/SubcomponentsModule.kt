package com.oletob.rpncalc.di

import com.oletob.rpncalc.ui.about.di.AboutComponent
import com.oletob.rpncalc.ui.history.di.HistoryComponent
import com.oletob.rpncalc.ui.history.di.HistoryModule
import com.oletob.rpncalc.ui.main.di.MainComponent
import dagger.Module

@Module(subcomponents = [MainComponent::class, AboutComponent::class, HistoryComponent::class])
class SubcomponentsModule