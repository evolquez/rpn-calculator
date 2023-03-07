package com.oletob.rpncalc.data.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.oletob.rpncalc.data.model.dao.MathOperationDao
import com.oletob.rpncalc.data.model.entity.MathOperation

@Database(entities = [MathOperation::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun mathOperationDao(): MathOperationDao
}