package com.yunho.queen.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.yunho.queen.domain.local.PatientChar

@Dao
interface PatientChartDao {
    @Insert
    fun insert(chart: PatientChar)

    @Delete
    fun delete(chart: PatientChar)

    @Query("UPDATE PatientChar SET text = :newText WHERE id = :id")
    fun updateTextById(id: Int, newText: String)
}