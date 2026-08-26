package com.oletob.rpncalc.di

import android.content.Context
import androidx.room.Room
import com.oletob.rpncalc.data.local.AppDatabase
import com.oletob.rpncalc.data.local.MathOperationDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "rpn-db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideMathOperationDao(db: AppDatabase): MathOperationDao = db.mathOperationDao()
}
