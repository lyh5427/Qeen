package com.yunho.queen.presentation.ui.patientDetail

import androidx.lifecycle.ViewModel
import com.yunho.queen.data.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PatientDetailModel @Inject constructor(
    private val repo: DataRepository
): ViewModel() {

}