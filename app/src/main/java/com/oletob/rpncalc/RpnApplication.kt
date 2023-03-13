package com.oletob.rpncalc

import android.app.Application
import com.oletob.rpncalc.di.ApplicationGraph
import com.oletob.rpncalc.di.DaggerApplicationGraph
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

open class RpnApplication: Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    val appGraph: ApplicationGraph by lazy {
        initGraph()
    }
    private fun initGraph(): ApplicationGraph = DaggerApplicationGraph.factory().create(applicationContext, applicationScope)
}