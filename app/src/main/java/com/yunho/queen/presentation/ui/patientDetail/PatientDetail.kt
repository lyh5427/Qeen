package com.yunho.queen.presentation.ui.patientDetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.yunho.queen.Util
import com.yunho.queen.databinding.ActivityPatientDetailBinding
import com.yunho.queen.domain.local.PatientChart
import com.yunho.queen.presentation.const.Action
import com.yunho.queen.presentation.const.Const
import com.yunho.queen.presentation.ui.addpatientchart.AddPatientChart
import com.yunho.queen.presentation.ui.patientDetail.adapter.PatientChartAdapter
import com.yunho.queen.singleClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PatientDetail : AppCompatActivity() {

    lateinit var binding: ActivityPatientDetailBinding
    private val model: PatientDetailModel by viewModels()

    lateinit var chartAdapter: PatientChartAdapter

    // 이미지 선택 콜백
    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            Util.saveImageCopyToAppStorage(
                context = this,
                uri = it,
                folderName = "my_folder"
            )
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatientDetailBinding.inflate(layoutInflater)

        setObserver()
        setListener()

        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        setView()
    }

    private fun setView() = with(binding) {
        val chartNum = intent.getStringExtra(Const.CHART_NUM)?: ""

        if (chartNum != "") {
            model.getPatientInfo(chartNum)
        }
    }

    private fun setObserver() = with(binding) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                model.action.collectLatest {
                    when (it.action) {
                        Action.SHOW_TOAST -> {
                            Util.showToast(this@PatientDetail, it.obj as String)
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                model.patientChartNumState.collectLatest {
                    when (it.action) {
                        Action.SET_TEXT -> patientChartNum.text = it.obj as String
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                model.patientNameState.collectLatest {
                    when (it.action) {
                        Action.SET_TEXT -> patientName.text = it.obj as String
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                model.chartRecyclerViewState.collectLatest {
                    when (it.action) {
                        Action.SET_ADAPTER -> setAdapter(it.obj as List<PatientChart>)
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {

            }
        }

    }

    private fun setListener() = with(binding) {
        btnAddImg.singleClickListener {
            pickImageFromGallery()
        }

        btnAddChart.singleClickListener {
            startActivity(
                Intent(this@PatientDetail, AddPatientChart::class.java)
                    .apply {
                        putExtra(Const.PATIENT_NAME, patientName.text)
                        putExtra(Const.CHART_NUM, patientChartNum.text)
                    }
            )
        }
    }

    // 갤러리에서 이미지 선택 요청
    private fun pickImageFromGallery() {
        pickImageLauncher.launch("image/*")
    }

    private fun setAdapter(itemList: List<PatientChart>) = with(binding) {
        chartAdapter = PatientChartAdapter(this@PatientDetail, itemList)
        chartRecyclerView.adapter = chartAdapter
        chartRecyclerView.layoutManager = LinearLayoutManager(this@PatientDetail)
    }
}