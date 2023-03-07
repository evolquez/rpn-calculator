package com.oletob.rpncalc.data.repository

import com.oletob.rpncalc.data.datasource.LocalDataSource
import com.oletob.rpncalc.data.model.entity.MathOperation
import javax.inject.Inject

class MathOperationRepository @Inject constructor(private val localDataSource: LocalDataSource) {

    fun addOperation(mathOperation: MathOperation) {
        localDataSource.addOperation(mathOperation)
    }

    fun getHistory(): List<MathOperation> = localDataSource.getHistory()

    fun clear() {
        localDataSource.clear()
    }
}