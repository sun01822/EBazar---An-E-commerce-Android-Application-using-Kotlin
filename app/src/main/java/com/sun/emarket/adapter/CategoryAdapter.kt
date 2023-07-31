package com.sun.emarket.adapter

// CategoryAdapter.kt

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.sun.emarket.R

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    private var categoryList: List<String> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(categories: List<String>) {
        categoryList = categories
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categoryList[position], position)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val categoryTextView: TextView = itemView.findViewById(R.id.categoryNameTextView)
        private val categoryCardView : CardView = itemView.findViewById(R.id.categoryCardView)
        fun bind(category: String, position: Int) {
            categoryTextView.text = category
            // Set different background colors based on the position
            val colors = itemView.resources.getIntArray(R.array.category_item_colors)
            val colorIndex = position % colors.size
            categoryCardView.setCardBackgroundColor(colors[colorIndex])
        }
    }
}
