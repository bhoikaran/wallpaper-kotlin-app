package com.example.wallpaperapplication.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.wallpaperapplication.R
import com.example.wallpaperapplication.WallpaperActivity
import com.squareup.picasso.Picasso

class WallpaperRvAdapter(
    private val wallpaperList: ArrayList<String>, private val context: Context
) : RecyclerView.Adapter<WallpaperRvAdapter.WallpaperItemViewHolder>() {
    class WallpaperItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wallpaperIv: ImageView = itemView.findViewById(R.id.iv_wallpaper)
        val wallpaperCv: CardView = itemView.findViewById(R.id.cv_wallpaper)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WallpaperItemViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.wallpaper_rv_item, parent, false)
        return WallpaperItemViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return wallpaperList.size
    }

    override fun onBindViewHolder(holder: WallpaperItemViewHolder, position: Int) {
       val wallpaperItem = wallpaperList[position]
        Picasso.get().load(wallpaperItem).into(holder.wallpaperIv)

        holder.wallpaperCv.setOnClickListener {
            val intent = Intent(holder.itemView.context, WallpaperActivity::class.java)
            intent.putExtra("imgUrl",wallpaperItem)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }


    }


}

