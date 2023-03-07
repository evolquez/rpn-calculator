package com.oletob.rpncalc.data.datasource

import com.oletob.rpncalc.data.model.dao.MathOperationDao
import com.oletob.rpncalc.data.model.entity.MathOperation
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val operationDao: MathOperationDao) {

    fun addOperation(operation: MathOperation) {
        operationDao.insert(operation)
    }

    fun getHistory(): List<MathOperation> = operationDao.history()

    fun clear() {
        operationDao.clear()
    }
}