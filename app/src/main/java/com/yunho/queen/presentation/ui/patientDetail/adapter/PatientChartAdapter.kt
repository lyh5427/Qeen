package com.yunho.queen.presentation.ui.patientDetail.adapter

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.animation.doOnEnd
import androidx.recyclerview.widget.RecyclerView
import com.yunho.queen.R
import com.yunho.queen.databinding.ItemPatientChartBinding
import com.yunho.queen.domain.local.PatientChart
import com.yunho.queen.presentation.const.Const
import com.yunho.queen.presentation.ui.addpatientchart.AddPatientChart
import com.yunho.queen.singleClickListener
import com.yunho.queen.toGone
import com.yunho.queen.toVisible

class PatientChartAdapter(
    private val context: Context,
    private val itemList: List<PatientChart>
): RecyclerView.Adapter<PatientChartAdapter.PatientChartListViewHolder>() {
    lateinit var binding: ItemPatientChartBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PatientChartListViewHolder {
        binding = ItemPatientChartBinding.inflate(LayoutInflater.from(context), parent, false)

        return PatientChartListViewHolder(binding)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(
        holder: PatientChartListViewHolder,
        position: Int
    ) {
        binding.title.text = itemList[position].title
        binding.chartContent.text = itemList[position].text

        // text 펼치고 접기
        binding.arrow.singleClickListener {
            binding.arrow
            if (binding.layoutContent.visibility == View.GONE) {
                animateLayoutHeight(binding.layoutContent, true)
                binding.arrow.setImageDrawable(context.getDrawable(R.mipmap.ic_arrow_up))
                binding.devider.toVisible()
            } else {
                animateLayoutHeight(binding.layoutContent, false)
                binding.arrow.setImageDrawable(context.getDrawable(R.mipmap.ic_arrow_down))
                binding.devider.toGone()
            }
        }

        // 수정하기
        binding.btnModify.singleClickListener {
            // 수정하기 창 열기
            context.startActivity(
                Intent(context, AddPatientChart::class.java)
                    .apply {
                        putExtra(
                            Const.PATIENT_NAME,
                            itemList[position].patientName
                        )

                        putExtra(
                            Const.PATIENT_CHART_TITLE,
                            itemList[position].title
                        )

                        putExtra(
                            Const.PATIENT_CHART_CONTENT,
                            itemList[position].text
                        )
                    }
            )
        }
    }

    override fun getItemCount(): Int = itemList.count()

    inner class PatientChartListViewHolder(val binding: ItemPatientChartBinding)
        : RecyclerView.ViewHolder(binding.root) { }


    fun animateLayoutHeight(view: View, show: Boolean) = with(binding) {
        val initialHeight = if (show) 0 else view.height
        view.measure(
            View.MeasureSpec.makeMeasureSpec((view.parent as View).width, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.UNSPECIFIED
        )
        val targetHeight = if (show) view.measuredHeight else 0

        if (show) {
            view.layoutParams.height = 0
            view.visibility = View.VISIBLE
        }

        val animator = ValueAnimator.ofInt(initialHeight, targetHeight)
        animator.addUpdateListener { animation ->
            val value = animation.animatedValue as Int
            view.layoutParams.height = value
            view.requestLayout()
        }

        animator.duration = 300L
        animator.doOnEnd {
            if (!show) view.visibility = View.GONE
        }

        animator.start()
    }
}