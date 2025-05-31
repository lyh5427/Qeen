package com.yunho.queen.data

import com.yunho.queen.data.local.dao.PatientInfoDao
import com.yunho.queen.domain.DataRepositorySource
import com.yunho.queen.domain.local.PatientInfo
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class DataRepository @Inject constructor(
    private val patientDb: PatientInfoDao
) : DataRepositorySource {
    override suspend fun insertPatient(info: PatientInfo) {
        patientDb.insert(info)
    }

    override suspend fun deletePatient(info: PatientInfo) {
        patientDb.delete(info)
    }

    override suspend fun getPatient(chart: String): PatientInfo? = patientDb.getPatient(chart)

    override suspend fun getAllPatientList(): List<PatientInfo> = patientDb.getAll()
}