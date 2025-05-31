package com.yunho.queen.domain

import com.yunho.queen.domain.local.PatientChart
import com.yunho.queen.domain.local.PatientInfo

interface DataRepositorySource {
    // PatientInfo 환자 DB
    suspend fun insertPatient(info: PatientInfo)
    suspend fun deletePatient(info: PatientInfo)
    suspend fun getPatient(chart: String): PatientInfo?
    suspend fun getAllPatientList(): List<PatientInfo>

    // PatientChart 환자 차트 디비
    suspend fun insertPatientChart(chart: PatientChart)
    suspend fun deletePatientChart(chart: PatientChart)
    suspend fun updatePatientChart(id: Int, newText: String)
    suspend fun getAllPatientChart(chartNum: String): List<PatientChart>?
}