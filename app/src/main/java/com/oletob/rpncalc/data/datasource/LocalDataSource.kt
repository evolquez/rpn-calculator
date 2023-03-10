package com.oletob.rpncalc.data.datasource

import com.oletob.rpncalc.data.model.dao.MathOperationDao
import com.oletob.rpncalc.data.model.entity.MathOperation
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val operationDao: MathOperationDao) {

    suspend fun addOperation(operation: MathOperation) {
        operationDao.insert(operation)
    }

    suspend fun getHistory() = operationDao.history()

    suspend fun clear() {
        operationDao.clear()
    }
}