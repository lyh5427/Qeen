package com.yunho.queen.presentation.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yunho.queen.domain.DataRepositorySource
import com.yunho.queen.domain.local.SendObject
import com.yunho.queen.presentation.const.Action
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: DataRepositorySource
): ViewModel() {

    // Common 변수
    private var _action: MutableSharedFlow<SendObject> =
        MutableSharedFlow(0, 1, BufferOverflow.SUSPEND)
    val action = _action.asSharedFlow()

    // PatientList
    private var _patientListState: MutableSharedFlow<SendObject> =
        MutableSharedFlow(0, 1, BufferOverflow.SUSPEND)
    var patientListState = _patientListState.asSharedFlow()

    private var _emptyListViewState: MutableSharedFlow<SendObject> =
        MutableSharedFlow(0, 1, BufferOverflow.SUSPEND)
    var emptyListViewState = _emptyListViewState.asSharedFlow()

    fun getPatientList() {
        viewModelScope.launch {
            val list = repo.getAllPatientList()

            Log.d("yunho", "${list.isNotEmpty()}")

            if (list.isNotEmpty()) {
                _patientListState.emit(SendObject(Action.SET_ADAPTER, list))
                _patientListState.emit(SendObject(Action.VISIBLE))
                _emptyListViewState.emit(SendObject(Action.GONE))
            } else {
                _patientListState.emit(SendObject(Action.GONE))
                _emptyListViewState.emit(SendObject(Action.VISIBLE))
            }
        }
    }
}