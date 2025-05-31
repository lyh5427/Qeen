package com.yunho.queen.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yunho.queen.data.local.dao.PatientInfoDao
import com.yunho.queen.domain.local.PatientInfo

@Database(entities = [PatientInfo::class], version = 2)
abstract class PatientDataBase: RoomDatabase() {
    abstract fun dao(): PatientInfoDao

}