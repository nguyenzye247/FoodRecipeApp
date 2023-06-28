package com.nguyenhl.bk.foodrecipe.feature.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream
import kotlin.math.max

object ImageUtils {
    fun scaleDownImage(inputImage: ByteArray, scaleFactor: Float): ByteArray {
        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
            BitmapFactory.decodeByteArray(inputImage, 0, inputImage.size, this)

            // Calculate the scaled width and height based on the provided scale factor
            val scaledWidth = (outWidth * scaleFactor).toInt()
            val scaledHeight = (outHeight * scaleFactor).toInt()

            // Reset inJustDecodeBounds flag for actual decoding
            inJustDecodeBounds = false

            // Calculate the sample size based on the desired scaled width and height
            inSampleSize = calculateInSampleSize(outWidth, outHeight, scaledWidth, scaledHeight)
        }

        // Decode the bitmap from the ByteArray with the specified options
        val scaledBitmap = BitmapFactory.decodeByteArray(inputImage, 0, inputImage.size, options)

        // Scale the bitmap to the desired width and height
        val finalBitmap = Bitmap.createScaledBitmap(
            scaledBitmap, options.outWidth, options.outHeight, false
        )

        // Compress the bitmap into a ByteArrayOutputStream with JPEG format and desired quality
        val outputStream = ByteArrayOutputStream()
        finalBitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)

        // Return the compressed image as a ByteArray
        return outputStream.toByteArray()
    }

    private fun calculateInSampleSize(
        imageWidth: Int,
        imageHeight: Int,
        desiredWidth: Int,
        desiredHeight: Int
    ): Int {
        var inSampleSize = 1

        if (imageWidth > desiredWidth || imageHeight > desiredHeight) {
            val widthRatio = imageWidth.toFloat() / desiredWidth.toFloat()
            val heightRatio = imageHeight.toFloat() / desiredHeight.toFloat()

            inSampleSize = max(widthRatio, heightRatio).toInt()
        }

        return inSampleSize
    }
}
