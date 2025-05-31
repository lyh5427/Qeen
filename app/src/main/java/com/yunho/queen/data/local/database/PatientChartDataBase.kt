package com.yunho.queen.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yunho.queen.data.local.dao.PatientChartDao
import com.yunho.queen.domain.local.PatientChart

@Database(entities = [PatientChart::class], version = 1)
abstract class PatientChartDataBase: RoomDatabase() {
    abstract fun dao(): PatientChartDao
}