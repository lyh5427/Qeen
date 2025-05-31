package com.yunho.queen.presentation.ui.patientDetail.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yunho.queen.databinding.ItemPatientChartBinding
import com.yunho.queen.domain.local.PatientChart

class PatientChartAdapter(
    private val context: Context,
    private val itemList: List<PatientChart>
): RecyclerView.Adapter<PatientChartAdapter.PatientChartListViewHolder>() {
    lateinit var binding: ItemPatientChartBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PatientChartListViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(
        holder: PatientChartListViewHolder,
        position: Int
    ) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    inner class PatientChartListViewHolder(val binding: ItemPatientChartBinding)
        : RecyclerView.ViewHolder(binding.root) { }
}