package com.yunho.queen.data

import com.yunho.queen.data.local.dao.PatientChartDao
import com.yunho.queen.data.local.dao.PatientInfoDao
import com.yunho.queen.domain.DataRepositorySource
import com.yunho.queen.domain.local.PatientChart
import com.yunho.queen.domain.local.PatientInfo
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class DataRepository @Inject constructor(
    private val patientDb: PatientInfoDao,
    private val patientChartDb: PatientChartDao
) : DataRepositorySource {
    override suspend fun insertPatient(info: PatientInfo) =
        patientDb.insert(info)

    override suspend fun deletePatient(info: PatientInfo) =
        patientDb.delete(info)

    override suspend fun getPatient(chart: String): PatientInfo? =
        patientDb.getPatient(chart)

    override suspend fun getAllPatientList(): List<PatientInfo> =
        patientDb.getAll()

    override suspend fun insertPatientChart(chart: PatientChart) =
        patientChartDb.insert(chart)

    override suspend fun deletePatientChart(chart: PatientChart) =
        patientChartDb.delete(chart)

    override suspend fun updatePatientChart(id: Int, newText: String) =
        patientChartDb.updateTextById(id, newText)

    override suspend fun getAllPatientChart(chartNum: String): List<PatientChart>? =
        patientChartDb.getAllPatientChart(chartNum)
}