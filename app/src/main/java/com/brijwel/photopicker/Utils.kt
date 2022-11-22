package com.brijwel.photopicker

import android.content.Context
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Build
import android.os.CancellationSignal
import android.provider.MediaStore
import android.util.Size
import java.io.File


fun Uri.getType(context: Context): String? = context.contentResolver.getType(this)
fun Uri.isVideo(context: Context): Boolean =
    context.contentResolver.getType(this)?.contains("video", true)
        ?: throw Exception("unknown type")

fun Uri.getThumbnail(context: Context): Bitmap? {
    val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
    val cursor = context.contentResolver.query(this, filePathColumn, null, null, null)
        ?: throw Exception("cursor not found")
    cursor.moveToFirst()

    val columnIndex = cursor.getColumnIndex(filePathColumn[0])
    val picturePath = cursor.getString(columnIndex)
    cursor.close()
    val mSize = Size(96, 96)
    val ca = CancellationSignal()

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        ThumbnailUtils.createVideoThumbnail(File(picturePath), mSize, ca)
    } else {
        @Suppress("DEPRECATION")
        ThumbnailUtils.createVideoThumbnail(picturePath, MediaStore.Video.Thumbnails.MICRO_KIND)
    }
}