package com.yunho.queen.domain

import com.yunho.queen.domain.local.PatientInfo

interface DataRepositorySource {
    suspend fun insertPatient(info: PatientInfo)
    suspend fun deletePatient(info: PatientInfo)
    suspend fun getPatient(chart: String): PatientInfo?
    suspend fun getAllPatientList(): List<PatientInfo>
}