package com.yunho.queen.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.yunho.queen.domain.local.PatientInfo

@Dao
interface PatientInfoDao {
    @Insert
    fun insert(info: PatientInfo)

    @Delete
    fun delete(info: PatientInfo)

    @Query("Select * From PatientInfo where charNum = :chart")
    fun getPatient(chart: String): PatientInfo?

    @Query("SELECT * From PatientInfo ORDER BY name ASC")
    fun getAll(): List<PatientInfo>
}