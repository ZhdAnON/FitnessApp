package com.zhdanon.fitnessapp.domain.util

import android.content.Context
import android.net.Uri
import java.io.File

fun uriToFile(context: Context, uri: Uri): File {
    val inputStream = context.contentResolver.openInputStream(uri)!!
    val tempFile = File(context.cacheDir, "upload_${System.currentTimeMillis()}.mp4")
    tempFile.outputStream().use { output ->
        inputStream.copyTo(output)
    }
    return tempFile
}