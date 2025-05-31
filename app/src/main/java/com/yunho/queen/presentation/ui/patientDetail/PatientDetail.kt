package com.yunho.queen.presentation.ui.patientDetail

import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.yunho.queen.R
import com.yunho.queen.Util
import com.yunho.queen.databinding.ActivityMainBinding
import com.yunho.queen.databinding.ActivityPatientDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PatientDetail : AppCompatActivity() {
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

    lateinit var binding: ActivityPatientDetailBinding
    private val model: PatientDetailModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatientDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }

    private fun setObserver() {

    }

    private fun setListener() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {

            }
        }
    }

    // 갤러리에서 이미지 선택 요청
    fun pickImageFromGallery() {
        pickImageLauncher.launch("image/*")
    }
}