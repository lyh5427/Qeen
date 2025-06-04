package com.yunho.queen.presentation.ui.patientDetail.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yunho.queen.databinding.ItemPatientImgBinding
import com.yunho.queen.singleClickListener
import com.yunho.queen.toGone
import java.io.File

class PatientImageAdapter(
    private val context: Context,
    private val imageList: List<File>,
    private val listener: PatientImageAdapterListener
) : RecyclerView.Adapter<PatientImageAdapter.PatientImageViewHolder>() {

    lateinit var binding: ItemPatientImgBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientImageViewHolder {
        binding = ItemPatientImgBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )

        return PatientImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PatientImageViewHolder, position: Int) {
        if (position < imageList.size) {
            val bitmap = BitmapFactory.decodeFile(imageList[position].absolutePath)
            holder.binding.img.setImageBitmap(bitmap)

            holder.binding.img.singleClickListener {
                listener.selectImage(imageList[position])
            }

            holder.binding.btnAddImg.toGone()
        } else {
            holder.binding.img.toGone()
            holder.binding.btnAddImg.singleClickListener {
                listener.selectImage(null)
            }
        }

    }

    override fun getItemCount(): Int {
        Log.d("yunho", "getItemCount() = ${imageList.size + 1}")
        return imageList.size + 1
    }

    inner class PatientImageViewHolder(val binding: ItemPatientImgBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }
}

interface PatientImageAdapterListener {
    fun selectImage(file: File?)
}