package com.oletob.rpncalc.data.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.oletob.rpncalc.data.model.dao.MathOperationDao
import com.oletob.rpncalc.data.model.entity.MathOperation

@Database(entities = [MathOperation::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun mathOperationDao(): MathOperationDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun create(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context, AppDatabase::class.java, "rpn-db").build()

                INSTANCE = instance

                instance
            }
        }
    }

}