package com.oletob.rpncalc.data.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.oletob.rpncalc.data.model.entity.MathOperation

@Dao
interface MathOperationDao {

    @Insert
    suspend fun insert(operation: MathOperation)

    @Query("SELECT * FROM math_operation")
    suspend fun history(): List<MathOperation>

    @Query("DELETE FROM math_operation")
    suspend fun clear()
}