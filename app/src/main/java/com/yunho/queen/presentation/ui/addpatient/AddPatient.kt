package com.yunho.queen.presentation.ui.addpatient

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.animation.AnimatableView.Listener
import com.yunho.queen.R
import com.yunho.queen.databinding.ActivityAddPatientBinding
import com.yunho.queen.domain.local.PatientInfo
import com.yunho.queen.presentation.const.Action
import com.yunho.queen.presentation.const.Const
import com.yunho.queen.singleClickListener
import com.yunho.queen.toDisable
import com.yunho.queen.toEnable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddPatient : AppCompatActivity() {

    lateinit var binding: ActivityAddPatientBinding
    private val model: AddPatientViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddPatientBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setObserver()
        setListener()
    }

    override fun onStart() {
        super.onStart()
        setView()
    }

    private fun setView() = with(binding) {
        val chart = intent.getStringExtra(Const.CHART) ?: ""

        if (chart != "") {
            model.getPatientInfo(chart)
        }
    }

    private fun setObserver() = with(binding) {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                model.action.collectLatest {
                    when (it.action) {
                        Action.SET_VIEW -> {
                            val patientInfo = it.obj as PatientInfo
                            setEditView(editChart, patientInfo.charNum)
                            setEditView(editName, patientInfo.name)
                        }

                        Action.FINISH -> {
                            finish()
                            showToast(resources.getString(R.string.save_success))
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                model.btnAddState.collectLatest {
                    when (it.action) {
                        Action.ENABLE -> btnAdd.toEnable()

                        Action.DISABLE -> btnAdd.toDisable()
                    }
                }
            }
        }
    }

    private fun setListener() = with(binding) {
        editChart.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

            override fun afterTextChanged(s: Editable?) {
                model.editChart(s.toString())
            }
        })

        editName.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

            override fun afterTextChanged(s: Editable?) {
                model.editName(s.toString())
            }
        })

        btnAdd.singleClickListener {
            model.addPatient()
        }
    }

    fun setEditView(view: EditText, text: String) {
        view.text = Editable.Factory.getInstance().newEditable(text)
    }

    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}