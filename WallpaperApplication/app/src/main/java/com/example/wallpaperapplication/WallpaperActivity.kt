package com.example.wallpaperapplication

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.wallpaperapplication.common_utility.CommonUtility.Companion.addImageToGallery
import com.example.wallpaperapplication.common_utility.CommonUtility.Companion.saveImageAndSetWallpaper
import com.example.wallpaperapplication.common_utility.CommonUtility.Companion.saveMediaToStorage
import com.squareup.picasso.Picasso
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.URL


lateinit var wallpaperIv : ImageView
lateinit var backIv : ImageView
lateinit var setWallpaperBtn : Button
lateinit var downloadWallpaperBtn : Button
lateinit var imageUrl : String

class WallpaperActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallpaper)

        wallpaperIv = findViewById(R.id.iv_wallpaper)
        backIv = findViewById(R.id.iv_back)
        setWallpaperBtn = findViewById(R.id.btn_set_wallpaper)
        downloadWallpaperBtn = findViewById(R.id.btn_download_wallpaper)
        imageUrl = intent.getStringExtra("imgUrl").toString()

        Picasso.get().load(imageUrl).into(wallpaperIv)
        backIv.setOnClickListener {
            onBackPressed()
        }
        setWallpaperBtn.setOnClickListener{
         /*   val bitmap = (wallpaperIv.drawable as BitmapDrawable).bitmap
            val wallpapaerManager = WallpaperManager.getInstance(baseContext)
            wallpapaerManager.setBitmap(bitmap)
            Toast.makeText(this,"Wallpaper set !", Toast.LENGTH_LONG).show()*/


            /*setWallpaperBtn.setOnClickListener {
                // Assuming imageUrl contains the resource name

                try {
                    // Use Glide to load the image from the URL into a bitmap
                    Glide.with(this)
                        .asBitmap()
                        .load(imageUrl)
                        .into(object : CustomTarget<Bitmap>() {
                            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                                // Set the downloaded bitmap as the wallpaper
                                val wallpaperManager = WallpaperManager.getInstance(this@WallpaperActivity)
                                try {
                                   *//* wallpaperManager.setBitmap(resource)
                                    Toast.makeText(this@WallpaperActivity, "Wallpaper set!", Toast.LENGTH_SHORT).show()*//*

                                    try {
                                        wallpaperManager.setBitmap(resource, null, true, WallpaperManager.FLAG_SYSTEM)
                                        Toast.makeText(this@WallpaperActivity, "Wallpaper set!", Toast.LENGTH_SHORT).show()

                                    } catch (e: IOException) {
                                        e.printStackTrace()
                                        Toast.makeText(this@WallpaperActivity, "Failed to set wallpaper", Toast.LENGTH_SHORT).show()
                                    }

                                } catch (e: IOException) {
                                    e.printStackTrace()
                                    // Handle error setting wallpaper
                                    Toast.makeText(this@WallpaperActivity, "Failed to set wallpaper", Toast.LENGTH_SHORT).show()
                                }
                            }

                            override fun onLoadCleared(placeholder: Drawable?) {
                                // Do nothing or handle the case when image loading is cleared
                            }
                        })
                } catch (e: Exception) {
                    e.printStackTrace()
                    // Handle error loading image
                    Toast.makeText(this@WallpaperActivity, "Failed to load image", Toast.LENGTH_SHORT).show()
                }
            }*/


            saveImageAndSetWallpaper(this)

        }
        downloadWallpaperBtn.setOnClickListener{

          /*  Log.d("downloadWallpaperBtn","downloadWallpaperBtn")
            val imageDownloader = ImageDownloader(this)
            imageDownloader.execute(imageUrl)*/
//            addImageToGallery(this, imageUrl)

            val bitmap = (wallpaperIv.drawable as BitmapDrawable).bitmap
            saveMediaToStorage(this, bitmap)
        }
    }


   /* fun setWallpaper(context: Context, imageResourceId: String) {
        val wallpaperManager = WallpaperManager.getInstance(context)

        try {
            val resId = context.resources.getIdentifier(imageResourceId, "drawable", context.packageName)

            if (resId != 0) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    val bitmap = BitmapFactory.decodeResource(context.resources, resId)
                    wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM)
                } else {
                    wallpaperManager.setResource(resId)
                }
            } else {
                // Resource not found
                Toast.makeText(context, "Resource not found: $imageResourceId", Toast.LENGTH_SHORT).show()
            }
        } catch (e: IOException) {
            e.printStackTrace()
            // Handle error
        }
    }*/



   /* private fun saveImageAndSetWallpaper() {

        val bitmap = (wallpaperIv.drawable as BitmapDrawable).bitmap
        val file = saveBitmapToFile(bitmap)
        setWallpaperFromFile(file)
    }


   private fun saveBitmapToFile(bitmap: Bitmap): File {
       val imagesDir = File(cacheDir, "images")
       imagesDir.mkdirs()
       val file = File(imagesDir, "wallpaper.jpg")
       FileOutputStream(file).use { outputStream ->
           bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
       }
       return file
   }

    private fun setWallpaperFromFile(file: File) {
        *//*val intent = Intent(Intent.ACTION_ATTACH_DATA)
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.setDataAndType(Uri.fromFile(file), "image/jpeg")
        intent.putExtra("mimeType", "image/jpeg")
        startActivity(Intent.createChooser(intent, "Set wallpaper"))*//*

        val uri = FileProvider.getUriForFile(
            this,
            "${packageName}.provider",
            file
        )
        val intent = Intent(Intent.ACTION_ATTACH_DATA)
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.setDataAndType(uri, "image/jpeg")
        intent.putExtra("mimeType", "image/jpeg")
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivity(Intent.createChooser(intent, "Set wallpaper"))

    }
*/


    fun downloadImage(url: String): InputStream? {
        return try {
            val connection = URL(url).openConnection()
            connection.connect()
            connection.getInputStream()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}