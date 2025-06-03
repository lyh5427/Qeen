package com.yunho.queen.presentation.ui.main.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yunho.queen.databinding.ItemPatientBinding
import com.yunho.queen.domain.local.PatientInfo
import com.yunho.queen.presentation.const.Const
import com.yunho.queen.presentation.ui.addpatient.AddPatient
import com.yunho.queen.presentation.ui.patientDetail.PatientDetail
import com.yunho.queen.singleClickListener

class PatientListAdapter(
    private val context: Context,
    private val itemList: List<PatientInfo>
): RecyclerView.Adapter<PatientListAdapter.PatientItemViewHolder>() {
    lateinit var binding: ItemPatientBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PatientListAdapter.PatientItemViewHolder {
        binding = ItemPatientBinding.inflate(LayoutInflater.from(context), parent, false)

        return PatientItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PatientListAdapter.PatientItemViewHolder, position: Int) {
        binding.patientNum.text = itemList[position].charNum
        binding.patientName.text = itemList[position].name

        binding.layoutItem.singleClickListener {
            startPatientDetail(position)
        }

        binding.imgMenu.singleClickListener {
            startAddPatientActivity(itemList[position])
        }
    }

    override fun getItemCount(): Int = itemList.count()

    inner class PatientItemViewHolder(val binding: ItemPatientBinding)
        : RecyclerView.ViewHolder(binding.root) {
    }

    private fun startAddPatientActivity(patientInfo: PatientInfo? = null) {
        val intent = Intent(context, AddPatient::class.java)

        if (patientInfo != null) {
            intent.putExtra(Const.CHART_NUM, patientInfo.charNum)
        }

        context.startActivity(intent)
    }

    private fun startPatientDetail(position: Int) {
        context.startActivity(
            Intent(context, PatientDetail::class.java)
                .apply {
                    putExtra(Const.CHART_NUM, itemList[position].charNum)
                }
        )
    }
}