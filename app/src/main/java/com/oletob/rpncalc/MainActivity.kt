package com.oletob.rpncalc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.oletob.rpncalc.ui.navigation.AppNavGraph
import com.oletob.rpncalc.ui.theme.RpnCalcTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RpnCalcTheme {
                AppNavGraph()
            }
        }
    }
}
