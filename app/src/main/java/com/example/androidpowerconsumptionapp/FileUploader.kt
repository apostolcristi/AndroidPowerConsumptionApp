package com.example.androidpowerconsumptionapp
//import com.google.api.services.drive.Drive
//import com.google.api.services.drive.model.File
//import java.io.FileInputStream
//
//class FileUploader(private val driveService: Drive) {
//
//    fun uploadFile(filePath: String, mimeType: String, folderId: String? = null): File {
//        val fileMetadata = File().apply {
//            name = java.io.File(filePath).name
//            parents = folderId?.let { listOf(it) }
//        }
//        val mediaContent = FileInputStream(filePath)
//        val file = driveService.files().create(fileMetadata, mediaContent).execute()
//        mediaContent.close()
//        return file
//    }
//}
