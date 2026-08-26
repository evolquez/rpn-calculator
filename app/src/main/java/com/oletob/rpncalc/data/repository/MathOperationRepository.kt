package com.oletob.rpncalc.data.repository

import com.oletob.rpncalc.data.local.MathOperation
import com.oletob.rpncalc.data.local.MathOperationDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MathOperationRepository @Inject constructor(
    private val mathOperationDao: MathOperationDao
) {

    fun observeHistory(): Flow<List<MathOperation>> = mathOperationDao.observeHistory()

    suspend fun addOperation(operation: MathOperation) = mathOperationDao.insert(operation)

    suspend fun clear() = mathOperationDao.clear()
}
