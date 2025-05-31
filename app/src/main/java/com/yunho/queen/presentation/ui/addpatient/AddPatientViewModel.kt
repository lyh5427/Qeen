package com.yunho.queen.presentation.ui.addpatient

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yunho.queen.domain.DataRepositorySource
import com.yunho.queen.domain.local.PatientInfo
import com.yunho.queen.domain.local.SendObject
import com.yunho.queen.presentation.const.Action
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPatientViewModel @Inject constructor(
    private val repo: DataRepositorySource
): ViewModel() {
    private var _action: MutableSharedFlow<SendObject> =
        MutableSharedFlow(0, 1, BufferOverflow.SUSPEND)
    val action = _action.asSharedFlow()

    private var _btnAddState: MutableSharedFlow<SendObject> =
        MutableSharedFlow(0, 1, BufferOverflow.SUSPEND)
    var btnAddState = _btnAddState.asSharedFlow()

    var patientChart: String = ""
    var patientName: String = ""

    fun getPatientInfo(chart: String) {
        viewModelScope.launch {
            val patientInfo = repo.getPatient(chart)

            if (patientInfo != null) {
                _action.emit(SendObject(Action.SET_VIEW, patientInfo))
            }
        }
    }

    fun editChart(chart: String) {
        viewModelScope.launch {
            patientChart = chart
            checkAddBtnState()
        }
    }

    fun editName(name: String) {
        viewModelScope.launch {
            patientName = name
            checkAddBtnState()
        }
    }

    fun checkAddBtnState() {
        viewModelScope.launch {
            _btnAddState.emit(
                SendObject(
                    if (patientChart != "" && patientName != "") {
                        Action.ENABLE
                    } else {
                        Action.DISABLE
                    })
            )
        }
    }

    fun addPatient() {
        viewModelScope.launch {
            repo.insertPatient(
                PatientInfo(
                    charNum = patientChart,
                    name = patientName
                )
            )

            _action.emit(SendObject(Action.FINISH))
        }
    }
}