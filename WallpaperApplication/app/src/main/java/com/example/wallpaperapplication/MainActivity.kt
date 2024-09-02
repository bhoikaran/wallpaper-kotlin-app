package com.example.wallpaperapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wallpaperapplication.adapter.CategoryRvAdapter
import com.example.wallpaperapplication.adapter.WallpaperRvAdapter
import com.example.wallpaperapplication.`interface`.RetrofitInterface
import com.example.wallpaperapplication.model.CategoryRvModel
import com.example.wallpaperapplication.model.Photos
import com.example.wallpaperapplication.model.WallpaperRvModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), CategoryRvAdapter.CategoryClickInterface {

    private lateinit var nestedSV: NestedScrollView
    private lateinit var wallpaperRv: RecyclerView
    private lateinit var categoryRv: RecyclerView
    private lateinit var wallpaperRvAdapter: WallpaperRvAdapter
    private lateinit var categoryRvAdapter: CategoryRvAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var pb_bottom_loading: ProgressBar
    private lateinit var searchEdit: EditText
    private lateinit var searchIv: ImageView
    private lateinit var retrofitInterface: RetrofitInterface

    private var current_page: Int = 0
    private var query: String = ""
    private val wallpaperList: ArrayList<String> = ArrayList()
    private var categoryList: List<CategoryRvModel> = ArrayList()
    private var apiInProgress : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.statusBarColor = getColor(R.color.light_black)

        wallpaperRv = findViewById(R.id.rv_wallpapers)
        categoryRv = findViewById(R.id.rv_categories)
        progressBar = findViewById(R.id.progress_bar)
        pb_bottom_loading = findViewById(R.id.pb_bottom_loading)
        searchEdit = findViewById(R.id.et_search)
        searchIv = findViewById(R.id.iv_search)
        nestedSV = findViewById(R.id.nestedSV)

        wallpaperRvAdapter = WallpaperRvAdapter(wallpaperList, applicationContext)
        wallpaperRv.layoutManager = GridLayoutManager(applicationContext, 2)
        wallpaperRv.adapter = wallpaperRvAdapter

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.pexels.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofitInterface = retrofit.create(RetrofitInterface::class.java)

        searchIv.setOnClickListener {
            if (searchEdit.text.toString().isNotEmpty()) {
                query = searchEdit.text.toString().trim()
                current_page = 0
                wallpaperList.clear()
                wallpaperRvAdapter.notifyDataSetChanged()
                getWallpaperByCategory()
            }
        }

        nestedSV.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if(!apiInProgress){
                if (scrollY == nestedSV.getChildAt(0).measuredHeight - nestedSV.measuredHeight) {
                    pb_bottom_loading.visibility = View.VISIBLE
                    getWallpaperByCategory()
                }
            }

        }

        getWallpaperByCategory()
        getCategories()
    }

    private fun getCategories() {
        categoryList = listOf(
            CategoryRvModel(
                "All",
                "https://images.pexels.com/photos/130621/pexels-photo-130621.jpeg?auto=compress&cs=tinysrgb&w=600"
            ),
            CategoryRvModel(
                "Nature",
                "https://images.pexels.com/photos/1062249/pexels-photo-1062249.jpeg?auto=compress&cs=tinysrgb&w=600"
            ),
            CategoryRvModel(
                "Architecture",
                "https://images.pexels.com/photos/443383/pexels-photo-443383.jpeg?auto=compress&cs=tinysrgb&w=600"
            ),
            CategoryRvModel(
                "Arts",
                "https://images.pexels.com/photos/1269968/pexels-photo-1269968.jpeg?auto=compress&cs=tinysrgb&w=600"
            ),
            CategoryRvModel(
                "Music",
                "https://images.pexels.com/photos/668295/pexels-photo-668295.jpeg?auto=compress&cs=tinysrgb&w=600"
            ),
            CategoryRvModel(
                "Cars",
                "https://images.pexels.com/photos/733745/pexels-photo-733745.jpeg?auto=compress&cs=tinysrgb&w=600"
            ),
            CategoryRvModel(
                "Flowers",
                "https://images.pexels.com/photos/1697912/pexels-photo-1697912.jpeg?auto=compress&cs=tinysrgb&w=600"
            ),
            CategoryRvModel(
                "Animal",
                "https://images.pexels.com/photos/3551498/pexels-photo-3551498.jpeg?auto=compress&cs=tinysrgb&w=600"
            )
        )

        categoryRvAdapter = CategoryRvAdapter(categoryList, this, this)
        categoryRv.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        categoryRv.adapter = categoryRvAdapter
        categoryRvAdapter.notifyDataSetChanged()
    }

    private fun getWallpaperByCategory() {
        apiInProgress = true
        var call: Call<WallpaperRvModel>? = null
        current_page++

        call = if (query.isNotEmpty() && query != "All") {
            retrofitInterface.getWallpaperByCategory(query, 30, current_page)
        } else {
            retrofitInterface.getWallpapers(current_page)
        }

        if (current_page == 1) {
            progressBar.visibility = View.VISIBLE
        }
        Log.d("current_page", "current_page$current_page");
        call!!.enqueue(object : Callback<WallpaperRvModel?> {
            override fun onResponse(
                call: Call<WallpaperRvModel?>,
                response: Response<WallpaperRvModel?>
            ) {
                progressBar.visibility = View.GONE
                pb_bottom_loading.visibility = View.GONE

                if (response.isSuccessful) {
                    val dt = response.body()
                    val photoLists: ArrayList<Photos> = dt!!.photos

                    if (photoLists.isNotEmpty()) {
                        val startPosition = wallpaperList.size
                        for (i in 0 until photoLists.size) {
                            val photoObj = photoLists[i]
                            val imgUrl: String = photoObj.src.portrait
                            wallpaperList.add(imgUrl)
                        }
                        val newItemCount = wallpaperList.size - startPosition
                        wallpaperRvAdapter.notifyItemRangeInserted(startPosition, newItemCount)
                    } else {
                        Toast.makeText(this@MainActivity, "No Wallpapers Found", Toast.LENGTH_LONG)
                            .show()
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Failed to get Response", Toast.LENGTH_LONG)
                        .show()
                }
                apiInProgress = false
            }

            override fun onFailure(call: Call<WallpaperRvModel?>, t: Throwable) {
                progressBar.visibility = View.GONE
                pb_bottom_loading.visibility = View.GONE
                apiInProgress = false
                Toast.makeText(
                    this@MainActivity,
                    "Failed to get wallpapers : $t",
                    Toast.LENGTH_LONG
                ).show()

            }
        })
    }

    override fun onCategoryClick(position: Int) {
        query = categoryList[position].categoryName
        current_page = 0
        wallpaperList.clear()
        wallpaperRvAdapter.notifyDataSetChanged()
        getWallpaperByCategory()
    }
}
