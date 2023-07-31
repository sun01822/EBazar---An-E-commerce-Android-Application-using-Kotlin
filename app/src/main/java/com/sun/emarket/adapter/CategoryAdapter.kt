package com.sun.emarket.adapter

// CategoryAdapter.kt
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.sun.emarket.R
import com.sun.emarket.fragments.categories.ElectronicsFragment
import com.sun.emarket.fragments.categories.JeweleryFragment
import com.sun.emarket.fragments.categories.MensClothingFragment
import com.sun.emarket.fragments.categories.WomensClothingFragment

class CategoryAdapter(private val fragmentManager: FragmentManager) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
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
        holder.bind(categoryList[position])
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val categoryTextView: TextView = itemView.findViewById(R.id.categoryNameTextView)
        private val categoryCardView: CardView = itemView.findViewById(R.id.categoryCardView)

        init {
            categoryCardView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val selectedCategory = categoryList[position]
                navigateToFragment(selectedCategory)
            }
        }

        private fun navigateToFragment(category: String) {
            val fragment: Fragment = when (category) {
                "jewelery" -> JeweleryFragment()
                "men's clothing" -> MensClothingFragment()
                "women's clothing" -> WomensClothingFragment()
                // Add more categories and corresponding fragments as needed
                else -> ElectronicsFragment()
            }

            // Perform fragment transaction to navigate to the selected fragment
            val transaction: FragmentTransaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.categoryContainer, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        fun bind(category: String) {
            categoryTextView.text = category
            // Set different background colors based on the position
            val colors = itemView.resources.getIntArray(R.array.category_item_colors)
            val colorIndex = adapterPosition % colors.size
            categoryCardView.setCardBackgroundColor(colors[colorIndex])
        }
    }
}
