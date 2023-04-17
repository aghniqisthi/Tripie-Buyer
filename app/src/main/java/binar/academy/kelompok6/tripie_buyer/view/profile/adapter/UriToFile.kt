package binar.academy.kelompok6.tripie_buyer.view.profile.adapter

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class UriToFile (context: Context) {
    private val applicationContext = context.applicationContext
    fun getImageBody(imageUri: Uri): File {
        val parcelFileDescriptor = applicationContext.contentResolver.openFileDescriptor(
            imageUri,
            "r",
            null
        )
        val file = File(
            applicationContext.cacheDir,
            applicationContext.contentResolver.getFileName(imageUri)
        )
        val inputStream = FileInputStream(parcelFileDescriptor?.fileDescriptor)
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)
        return file
    }

    @SuppressLint("Range")
    private fun ContentResolver.getFileName(imageUri: Uri): String {
        var name = ""
        val cursor = query(
            imageUri, null, null,
            null, null
        )
        cursor?.use {
            it.moveToFirst()
            name = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
        }
        return name
    }
}
