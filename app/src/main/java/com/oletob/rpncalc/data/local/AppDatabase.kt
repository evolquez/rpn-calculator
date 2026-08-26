package com.oletob.rpncalc.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [MathOperation::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mathOperationDao(): MathOperationDao
}
