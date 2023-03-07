package com.oletob.rpncalc.di

import android.content.Context
import androidx.room.Room
import com.oletob.rpncalc.data.model.dao.MathOperationDao
import com.oletob.rpncalc.data.model.database.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun providesMathOperationDao(applicationContext: Context): MathOperationDao {
        return Room
            .databaseBuilder(applicationContext, AppDatabase::class.java, "rpn-db")
            .allowMainThreadQueries()
            .build().mathOperationDao()
    }
}