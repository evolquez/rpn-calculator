package com.oletob.rpncalc.feature.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oletob.rpncalc.data.local.MathOperation
import com.oletob.rpncalc.data.repository.MathOperationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val mathOperationRepository: MathOperationRepository
) : ViewModel() {

    val history: StateFlow<List<MathOperation>> = mathOperationRepository.observeHistory()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun clearHistory() {
        viewModelScope.launch {
            mathOperationRepository.clear()
        }
    }
}
