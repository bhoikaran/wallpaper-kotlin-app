package com.example.wallpaperapplication

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.example.wallpaperapplication.common_utility.CommonUtility
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL

class ImageDownloader(private val context: Context) : AsyncTask<String, Void, Bitmap?>() {
    override fun doInBackground(vararg params: String): Bitmap? {
        val imageUrl = params[0]

        try {
            val url = URL(imageUrl)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()

            val inputStream: InputStream = connection.inputStream
            return BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            // Show a Toast message for error
//            showToast("Error downloading image: ${e.message}")
           CommonUtility.showToast(context,"Error downloading image: ${e.message}")
        }
        return null
    }


    override fun onPostExecute(result: Bitmap?) {
        // Do something with the downloaded image
        if (result != null) {

            val savedUri = saveImageToGallery(result)
            if (savedUri != null) {
                // Show a Toast message indicating success
                Toast.makeText(context, "Image saved to gallery", Toast.LENGTH_SHORT).show()
            } else {
                // Failed to save image to gallery
                Toast.makeText(context, "Failed to save image to gallery", Toast.LENGTH_SHORT).show()
            }
//            Log.d("result","result : "+result.toString());
//            Toast.makeText(context, "Failed to Download image : "+result.toString(), Toast.LENGTH_SHORT).show()
        } else {
            // Failed to download image
            Toast.makeText(context, "Failed to Download image", Toast.LENGTH_SHORT).show()
        }
    }



    private fun saveImageToGallery(bitmap: Bitmap): String? {
        // Create a file to save the image
        val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val imageFile = File(imagesDir, "image_${System.currentTimeMillis()}.jpg")
        try {
            val outputStream: OutputStream = FileOutputStream(imageFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.flush()
            outputStream.close()

            // Insert the image into the device's MediaStore database
            val contentValues = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, imageFile.name)
                put(MediaStore.Images.Media.DESCRIPTION, "Image downloaded from the internet")
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                put(MediaStore.Images.Media.DATA, imageFile.absolutePath)
            }
            val uri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            return uri?.toString()
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }


}