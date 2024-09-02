package com.example.wallpaperapplication.common_utility

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.provider.MediaStore.Images
import android.util.Log
import android.widget.Toast
import androidx.core.content.FileProvider
import com.example.wallpaperapplication.wallpaperIv
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


class CommonUtility {



    companion object {
        fun showToast(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
        fun addImageToGallery(context: Context, filePath: String){
         /*   Log.d("addImageToGallery","addImageToGallery")
            val values = ContentValues()

            values.put(Images.Media.DATE_TAKEN, System.currentTimeMillis())
            values.put(Images.Media.MIME_TYPE, "image/jpeg")
            values.put(MediaStore.MediaColumns.DATA, filePath)

            context.contentResolver.insert(Images.Media.EXTERNAL_CONTENT_URI, values)*/
        }

         fun saveImageAndSetWallpaper(context: Context) {

            val bitmap = (wallpaperIv.drawable as BitmapDrawable).bitmap
            val file = saveBitmapToFile(context,bitmap)
            setWallpaperFromFile(context,file)
        }


        private fun saveBitmapToFile(context: Context, bitmap: Bitmap): File {
            val imagesDir = File(context.cacheDir, "images")
            imagesDir.mkdirs()
            val file = File(imagesDir, "wallpaper.jpg")
            FileOutputStream(file).use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            }
            return file
        }

         private fun setWallpaperFromFile(context: Context, file: File) {
            /*val intent = Intent(Intent.ACTION_ATTACH_DATA)
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            intent.setDataAndType(Uri.fromFile(file), "image/jpeg")
            intent.putExtra("mimeType", "image/jpeg")
            startActivity(Intent.createChooser(intent, "Set wallpaper"))*/

            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.provider",
                file
            )
            val intent = Intent(Intent.ACTION_ATTACH_DATA)
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            intent.setDataAndType(uri, "image/jpeg")
            intent.putExtra("mimeType", "image/jpeg")
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
             context.startActivity(Intent.createChooser(intent, "Set wallpaper"))

        }




        fun saveMediaToStorage(context: Context,bitmap: Bitmap) {
            //Generating a file name
            val filename = "${System.currentTimeMillis()}.jpg"

            //Output stream
            var fos: OutputStream? = null

            //For devices running android >= Q
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                //getting the contentResolver
                context?.contentResolver?.also { resolver ->

                    //Content resolver will process the contentvalues
                    val contentValues = ContentValues().apply {

                        //putting file information in content values
                        put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                    }

                    //Inserting the contentValues to contentResolver and getting the Uri
                    val imageUri: Uri? =
                        resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                    //Opening an outputstream with the Uri that we got
                    fos = imageUri?.let { resolver.openOutputStream(it) }
                }
            } else {
                //These for devices running on android < Q
                //So I don't think an explanation is needed here
                val imagesDir =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                val image = File(imagesDir, filename)
                fos = FileOutputStream(image)
            }

            fos?.use {
                //Finally writing the bitmap to the output stream that we opened
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
                showToast(context,"Saved to Photos")

            }
        }
    }


}