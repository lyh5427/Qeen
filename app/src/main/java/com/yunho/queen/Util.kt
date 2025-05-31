package com.yunho.queen

class Util {
    companion object {
//        // 이미지 선택 콜백
//        private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
//            uri?.let {
//                saveImageCopyToAppStorage(
//                    uri = it,
//                    folderName = "my_folder",
//                    fileName = "custom_name.jpg"
//                )
//            }
//        }
//
//        // 갤러리에서 이미지 선택 요청
//        fun pickImageFromGallery() {
//            pickImageLauncher.launch("image/*")
//        }
//
//        // 이미지 복사본을 앱 전용 외부 저장소에 저장
//        fun saveImageCopyToAppStorage(uri: Uri, folderName: String, fileName: String) {
//            try {
//                // 저장 폴더 만들기
//                val targetDir = File(getExternalFilesDir(null), folderName)
//                if (!targetDir.exists()) targetDir.mkdirs()
//
//                val targetFile = File(targetDir, fileName)
//
//                // 복사 진행
//                contentResolver.openInputStream(uri)?.use { inputStream ->
//                    FileOutputStream(targetFile).use { outputStream ->
//                        inputStream.copyTo(outputStream)
//                    }
//                }
//
//                Toast.makeText(this, "복사본 저장 완료: ${targetFile.absolutePath}", Toast.LENGTH_SHORT).show()
//
//            } catch (e: Exception) {
//                e.printStackTrace()
//                Toast.makeText(this, "이미지 저장 실패: ${e.message}", Toast.LENGTH_LONG).show()
//            }
//        }
//
//    }
//
//    fun loadImagesFromAppStorage(folderName: String): List<File> {
//        val dir = File(getExternalFilesDir(null), folderName)
//        if (!dir.exists()) return emptyList()
//
//        return dir.listFiles { file ->
//            file.isFile && file.extension.lowercase() in listOf("jpg", "jpeg", "png", "webp")
//        }?.toList() ?: emptyList()
    }
}