package com.oletob.rpncalc

import android.app.Application
import com.oletob.rpncalc.di.ApplicationGraph
import com.oletob.rpncalc.di.DaggerApplicationGraph

open class RpnApplication: Application() {

    val appGraph: ApplicationGraph by lazy {
        initGraph()
    }
    private fun initGraph(): ApplicationGraph = DaggerApplicationGraph.factory().create()
}