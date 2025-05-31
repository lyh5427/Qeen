package com.yunho.queen.domain.local

import androidx.room.Entity
import androidx.room.PrimaryKey

// 데이터베이스
@Entity
data class PatientInfo(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var charNum: String,
    var name: String,
    var regDT: Long = System.currentTimeMillis(),
)

@Entity
data class PatientChar(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var text: String,
    var regDT: Long = System.currentTimeMillis(),
)

@Entity
data class PatientImage(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var imageName: String

)

// 앱내에서 쓰는 DTO
data class SendObject(
    val action: String,
    val obj: Any? = null
)
