package com.example.wallpaperapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wallpaperapplication.R
import com.example.wallpaperapplication.model.CategoryRvModel
import com.squareup.picasso.Picasso

class CategoryRvAdapter(
    private val categoryList: List<CategoryRvModel>,
    private val context: Context,
    private val categoryClickInterface: CategoryClickInterface
) :
    RecyclerView.Adapter<CategoryRvAdapter.CategoryItemViewHolder>() {
    class CategoryItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryIv: ImageView = itemView.findViewById(R.id.iv_category)
        val categoryTv: TextView = itemView.findViewById(R.id.tv_category)
    }

    interface CategoryClickInterface {
        fun onCategoryClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.category_rv_item, parent, false)
        return CategoryItemViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: CategoryItemViewHolder, position: Int) {
        val categoryItem = categoryList[position]
        holder.categoryTv.text = categoryItem.categoryName
        Picasso.get().load(categoryItem.categoryImg).placeholder(R.drawable.wallpaper_icon).into(holder.categoryIv)
        holder.itemView.setOnClickListener{
            categoryClickInterface.onCategoryClick(position)
        }
    }
}