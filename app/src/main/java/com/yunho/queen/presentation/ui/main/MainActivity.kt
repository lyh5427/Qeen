package com.yunho.queen.presentation.ui.main

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.yunho.queen.R
import com.yunho.queen.databinding.ActivityMainBinding
import com.yunho.queen.presentation.ui.main.fragment.PatientListFragment
import com.yunho.queen.presentation.ui.main.fragment.StudyFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val model: MainViewModel by viewModels()

    private lateinit var patientFragment: Fragment
    private lateinit var studyFragment: Fragment

    private var permList: Array<String> = arrayOf(
        Manifest.permission.READ_MEDIA_IMAGES,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    private var permissionLauncher: ActivityResultLauncher<Array<String>>
            = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { list ->
                list.forEach { perm, isGrant ->
                    if (isGrant) {
                        Toast.makeText(this,
                            "권한이 허용되었습니다..",
                            Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this,
                            "권한이 필요합니다.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        reqPerm()
        setView()
    }

    private fun setView() = with(binding) {
        patientFragment = PatientListFragment()
        studyFragment = StudyFragment()

        supportFragmentManager.beginTransaction().replace(
            R.id.mainFragment, patientFragment
        ).commit()

        setListener()
    }

    private fun setListener() = with(binding) {
        bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.page_patient -> {
                    navigateFragment(patientFragment)
                    return@setOnItemSelectedListener true
                }

                R.id.page_study -> {
                    navigateFragment(studyFragment)
                    return@setOnItemSelectedListener true
                }

                else -> {
                    return@setOnItemSelectedListener false
                }
            }
        }
    }

    private fun navigateFragment(replaceFragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFragment, replaceFragment)
            .commitAllowingStateLoss()
    }



    fun reqPerm() {
        permissionLauncher.launch(permList)
    }
}