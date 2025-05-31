package com.yunho.queen

import android.content.Context
import android.net.Uri
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Util {
    companion object {
        /**갤러리에서 선택한 이미지의 복사본을 앱 전용 외부 폴더 폴더를 만들어 저장
         * 환자별로 폴더 각각 생성
         * 이미지 이름은 저장 당시 시간의 yyyy-MM-DDHHMMSS 형식
         * 폴더 생성 형식에 어긋날 때 예외 처리 필요
         *
         * @param context Activity의 context
         * @param url 갤러리에서 가져온 이미지 경로
         * @param folderName 만들어줄 폴더 이름 (환자이름으로 생성)
         * */
        fun saveImageCopyToAppStorage(context: Context, uri: Uri, folderName: String) {
            try {
                // 저장 폴더 만들기
                val targetDir = File(context.getExternalFilesDir(null), folderName)
                if (!targetDir.exists()) targetDir.mkdirs()

                val targetFile = File(targetDir, getCurrentDateTimeString())

                // 복사 진행
                context.contentResolver.openInputStream(uri)?.use { inputStream ->
                    FileOutputStream(targetFile).use { outputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }

                Toast.makeText(
                    context,
                    "이미지 저장 완료",
                    Toast.LENGTH_SHORT
                ).show()

            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(
                    context,
                    "이미지 저장 실패",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        /**
         * 앱 전용 외부저장소에 저장된 이미지를 로드할 때 사용할 파일 목록을 검색하는 함수
         * @param context Activity의 context
         * @param folderName 리스트를 불러올 폴더 이름 (환자 이름)
         * */
        fun loadImagesFromAppStorage(context: Context, folderName: String): List<File> {
            val dir = File(context.getExternalFilesDir(null), folderName)
            if (!dir.exists()) return emptyList()

            return dir.listFiles { file ->
                file.isFile && file.extension.lowercase() in listOf("jpg", "jpeg", "png", "webp")
            }?.toList() ?: emptyList()
        }

        fun getCurrentDateTimeString(): String {
            val dateFormat = SimpleDateFormat("yyyy-MM-DDHHMMSS", Locale.getDefault())
            return dateFormat.format(Date())
        }
    }
}