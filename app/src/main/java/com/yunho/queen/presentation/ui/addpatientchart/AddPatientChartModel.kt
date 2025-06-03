package com.yunho.queen.presentation.ui.addpatientchart

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yunho.queen.R
import com.yunho.queen.domain.DataRepositorySource
import com.yunho.queen.domain.local.PatientChart
import com.yunho.queen.domain.local.SendObject
import com.yunho.queen.presentation.const.Action
import com.yunho.queen.presentation.const.Const.CHART_NUM
import com.yunho.queen.presentation.const.Const.PATIENT_CHART_CONTENT
import com.yunho.queen.presentation.const.Const.PATIENT_CHART_TITLE
import com.yunho.queen.presentation.const.Const.PATIENT_NAME
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPatientChartModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repo: DataRepositorySource
): ViewModel()  {

    // Common 변수
    private var _action: MutableSharedFlow<SendObject> =
        MutableSharedFlow(0, 1, BufferOverflow.SUSPEND)
    val action = _action.asSharedFlow()

    private var _btnSaveState: MutableSharedFlow<SendObject> =
        MutableSharedFlow(0, 1, BufferOverflow.SUSPEND)
    val btnSaveState = _btnSaveState.asSharedFlow()

    private var _editChartTitleState: MutableSharedFlow<SendObject> =
        MutableSharedFlow(0, 1, BufferOverflow.SUSPEND)
    val editChartTitleState = _editChartTitleState.asSharedFlow()

    private var _editChartContentState: MutableSharedFlow<SendObject> =
        MutableSharedFlow(0, 1, BufferOverflow.SUSPEND)
    val editChartContentState = _editChartContentState.asSharedFlow()

    var patientName: String = ""
    var patientChartNum: String = ""
    var patientChartTitle: String = ""
    var patientChartContent: String = ""

    // repo 접근 함수
    fun insertPatientChart() {
        viewModelScope.launch {
            val patientChart = PatientChart(
                charNum = patientChartNum,
                patientName = patientName,
                title = patientChartTitle,
                text = patientChartContent
            )

            repo.insertPatientChart(patientChart)
            _action.emit(
                SendObject(
                    Action.SHOW_TOAST,
                    context.resources.getString(R.string.save_success)
                )
            )
            _action.emit(SendObject(Action.FINISH))
        }
    }

    fun setData(intent: Intent) {
        viewModelScope.launch {
            patientName = intent.getStringExtra(PATIENT_NAME)?: ""
            patientChartNum = intent.getStringExtra(CHART_NUM)?: ""
            patientChartTitle = intent.getStringExtra(PATIENT_CHART_TITLE)?: ""
            patientChartContent = intent.getStringExtra(PATIENT_CHART_CONTENT)?: ""

            if (patientChartTitle != "") {
                _editChartTitleState.emit(SendObject(Action.SET_TEXT, patientChartTitle))
            }

            if (patientChartContent != "") {
                _editChartContentState.emit(SendObject(Action.SET_TEXT, patientChartContent))
            }
        }
    }

    fun setChartTitle(chart: String) {
        patientChartTitle = chart

        setSaveBtnState()
    }

    fun setChartContent(content: String) {
        patientChartContent = content

        setSaveBtnState()
    }

    fun setSaveBtnState() {
        viewModelScope.launch {
            _btnSaveState.emit(
                SendObject(
                    if (patientChartTitle != "" && patientChartContent != "") {
                        Action.ENABLE
                    } else {
                        Action.DISABLE
                    }
                )
            )
        }
    }

}