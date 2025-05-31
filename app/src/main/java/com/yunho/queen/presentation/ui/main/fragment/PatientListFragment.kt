package com.yunho.queen.presentation.ui.main.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.yunho.queen.databinding.FragmentPatientBinding
import com.yunho.queen.domain.local.PatientInfo
import com.yunho.queen.presentation.const.Action
import com.yunho.queen.presentation.ui.addpatient.AddPatient
import com.yunho.queen.presentation.ui.main.MainViewModel
import com.yunho.queen.presentation.ui.main.adapter.PatientListAdapter
import com.yunho.queen.singleClickListener
import com.yunho.queen.toGone
import com.yunho.queen.toVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PatientListFragment : Fragment() {
    lateinit var binding: FragmentPatientBinding
    val model: MainViewModel by activityViewModels<MainViewModel>()

    lateinit var adapter: PatientListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPatientBinding.inflate(inflater, container, false)

        setObserver()
        setListener()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        model.getPatientList()
    }

    private fun setObserver() = with(binding) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                model.action.collect {
                    when (it.action) {
                        Action.SET_ADAPTER -> {
                            setAdapter(it.obj as List<PatientInfo>)
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                model.patientListState.collect {
                    when (it.action) {
                        Action.VISIBLE -> patientList.toVisible()

                        Action.GONE -> patientList.toGone()

                        Action.SET_ADAPTER -> {
                            Log.d("yunho", "${it.obj as List<PatientInfo>}")
                            setAdapter(it.obj as List<PatientInfo>)
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                model.emptyListViewState.collectLatest {
                    when (it.action) {
                        Action.VISIBLE -> emptyListView.toVisible()

                        Action.GONE -> emptyListView.toGone()
                    }
                }
            }
        }
    }

    private fun setListener() = with(binding) {
        btnAddPatient.singleClickListener {
            startAddPatientActivity()
        }
    }

    private fun setAdapter(item: List<PatientInfo>) = with(binding) {
        adapter = PatientListAdapter(
            context = requireContext(),
            itemList = item
        )

        patientList.adapter = adapter
        patientList.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun startAddPatientActivity() {
        startActivity(Intent(requireContext(), AddPatient::class.java))
    }
}