package com.yunho.queen.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.yunho.queen.domain.local.PatientChart

@Dao
interface PatientChartDao {
    @Insert
    fun insert(chart: PatientChart)

    @Delete
    fun delete(chart: PatientChart)

    @Query("UPDATE PatientChart SET text = :newText WHERE id = :id")
    fun updateTextById(id: Int, newText: String)

    @Query("Select * From PatientChart Where charNum = :chartNum")
    fun getAllPatientChart(chartNum: String): List<PatientChart>?
}