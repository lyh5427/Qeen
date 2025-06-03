package com.yunho.queen.presentation.ui.addpatientchart

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.yunho.queen.R
import com.yunho.queen.Util
import com.yunho.queen.databinding.ActivityAddPatientChartBinding
import com.yunho.queen.presentation.const.Action
import com.yunho.queen.presentation.ui.addpatient.AddPatientViewModel
import com.yunho.queen.singleClickListener
import com.yunho.queen.toDisable
import com.yunho.queen.toEnable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddPatientChart : AppCompatActivity() {

    lateinit var binding: ActivityAddPatientChartBinding
    private val model: AddPatientChartModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPatientChartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setObserver()
        setListener()
        model.setData(intent)
    }

    private fun setObserver() = with(binding) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                model.action.collect {
                    when (it.action) {
                        Action.SHOW_TOAST -> {
                            Util.showToast(
                                this@AddPatientChart,
                                it.obj as String
                            )
                        }

                        Action.FINISH -> finish()
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                model.btnSaveState.collect {
                    when (it.action) {
                        Action.ENABLE -> btnSave.toEnable()

                        Action.DISABLE -> btnSave.toDisable()
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                model.editChartTitleState.collect {
                    when (it.action) {
                        Action.SET_TEXT -> {
                            editChartTitle.text =
                                Editable.Factory.getInstance().newEditable(it.obj as String)
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                model.editChartContentState.collect {
                    when (it.action) {
                        Action.SET_TEXT -> {
                            editChartContent.text =
                                Editable.Factory.getInstance().newEditable(it.obj as String)
                        }
                    }
                }
            }
        }
    }

    private fun setListener() = with(binding) {
        editChartTitle.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

            override fun afterTextChanged(s: Editable?) {
                model.setChartTitle(s.toString())
            }
        })

        editChartContent.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

            override fun afterTextChanged(s: Editable?) {
                model.setChartContent(s.toString())
            }
        })

        btnSave.singleClickListener {
            model.insertPatientChart()
        }
    }
}