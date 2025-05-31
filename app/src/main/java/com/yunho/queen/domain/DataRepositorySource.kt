package com.yunho.queen.domain

import com.yunho.queen.domain.local.PatientInfo

interface DataRepositorySource {
    fun insertPatient(info: PatientInfo)
    fun deletePatient(info: PatientInfo)
    fun getPatient(chart: String): PatientInfo?
    fun getAllPatientList(): List<PatientInfo>
}