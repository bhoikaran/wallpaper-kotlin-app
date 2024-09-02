package com.example.wallpaperapplication.model

data class WallpaperRvModel(
    var page:Int,
    var per_page:Int,
    var photos:ArrayList<Photos>,
    var total_results:Int,
    var next_page:String
)