package com.oletob.rpncalc.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MathOperationDao {

    @Insert
    suspend fun insert(operation: MathOperation)

    @Query("SELECT * FROM math_operation ORDER BY timestamp DESC")
    fun observeHistory(): Flow<List<MathOperation>>

    @Query("DELETE FROM math_operation")
    suspend fun clear()
}
