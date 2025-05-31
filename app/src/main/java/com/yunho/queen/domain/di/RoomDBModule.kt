package com.yunho.queen.domain.di

import android.content.Context
import androidx.room.Room
import com.yunho.queen.data.local.dao.PatientChartDao
import com.yunho.queen.data.local.dao.PatientInfoDao
import com.yunho.queen.data.local.database.PatientChartDataBase
import com.yunho.queen.data.local.database.PatientDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomDBModule {
    @Singleton
    @Provides
    fun providesPatientInfoDao(db: PatientDataBase): PatientInfoDao = db.dao()

    @Singleton
    @Provides
    fun providesPatientInfoBase(@ApplicationContext context: Context): PatientDataBase =
        Room.databaseBuilder(
            context,
            PatientDataBase::class.java, "PatientInfo.db"
        ).fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()


    @Singleton
    @Provides
    fun providesPatientChartDao(db: PatientChartDataBase): PatientChartDao = db.dao()

    @Singleton
    @Provides
    fun providesPatientChartBase(@ApplicationContext context: Context): PatientChartDataBase =
        Room.databaseBuilder(
            context,
            PatientChartDataBase::class.java, "PatientChart.db"
        ).fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
}