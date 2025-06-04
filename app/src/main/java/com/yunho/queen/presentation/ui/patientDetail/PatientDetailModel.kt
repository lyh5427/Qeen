package com.yunho.queen.presentation.ui.patientDetail

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yunho.queen.R
import com.yunho.queen.Util
import com.yunho.queen.data.DataRepository
import com.yunho.queen.domain.local.PatientInfo
import com.yunho.queen.domain.local.SendObject
import com.yunho.queen.presentation.const.Action
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class PatientDetailModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repo: DataRepository
): ViewModel() {
    // Common 변수
    private var _action: MutableSharedFlow<SendObject> =
        MutableSharedFlow(0, 1, BufferOverflow.SUSPEND)
    val action = _action.asSharedFlow()

    private var _patientChartNumState: MutableSharedFlow<SendObject> =
        MutableSharedFlow(0, 1, BufferOverflow.SUSPEND)
    val patientChartNumState = _patientChartNumState.asSharedFlow()

    private var _patientNameState: MutableSharedFlow<SendObject> =
        MutableSharedFlow(0, 1, BufferOverflow.SUSPEND)
    val patientNameState = _patientNameState.asSharedFlow()

    private var _imgRecyclerViewState: MutableSharedFlow<SendObject> =
        MutableSharedFlow(0, 1, BufferOverflow.SUSPEND)
    val imgRecyclerViewState =_imgRecyclerViewState.asSharedFlow()

    private var _chartRecyclerViewState: MutableSharedFlow<SendObject> =
        MutableSharedFlow(0, 1, BufferOverflow.SUSPEND)
    val chartRecyclerViewState =_chartRecyclerViewState.asSharedFlow()

    var patientInfo: PatientInfo? = null

    fun getPatientInfo(chartNum: String) {
        viewModelScope.launch {
            patientInfo = repo.getPatient(chartNum)
            val patientChartList = repo.getAllPatientChart(chartNum)

            if (patientInfo != null) {
                _patientChartNumState.emit(SendObject(Action.SET_TEXT, patientInfo!!.charNum))
                _patientNameState.emit(SendObject(Action.SET_TEXT, patientInfo!!.name))

                setImageAdapter()
            } else {
                _action.emit(
                    SendObject(
                        Action.SHOW_TOAST,
                        context.getString(R.string.not_found_patient_err)
                    )
                )
            }

            if (patientChartList.isNullOrEmpty()) {
                _action.emit(
                    SendObject(
                        Action.SHOW_TOAST,
                        context.getString(R.string.not_found_patient_chart_err)
                    )
                )
            } else {
                _chartRecyclerViewState.emit(
                    SendObject(
                        Action.SET_ADAPTER,
                        patientChartList
                    )
                )
            }
        }
    }

    fun saveImageUri(uri: Uri) {
        Util.saveImageCopyToAppStorage(
            context = context,
            uri = uri,
            folderName = patientInfo!!.name
        )
        setImageAdapter()
    }

    fun setImageAdapter() {
        viewModelScope.launch {
            var imgList: List<File> = listOf()

            imgList = Util.loadImagesFromAppStorage(context, patientInfo!!.name)

            Log.d("yunho", "${imgList}aaaa")

            _imgRecyclerViewState.emit(SendObject(Action.VISIBLE))
            _imgRecyclerViewState.emit(SendObject(Action.SET_ADAPTER, imgList))
        }
    }
}