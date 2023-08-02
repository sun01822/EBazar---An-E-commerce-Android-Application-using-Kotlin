package com.sun.emarket.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sun.emarket.databinding.ItemProductBinding
import com.sun.emarket.model.Product
import com.bumptech.glide.Glide
import com.sun.emarket.R

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private var productList: List<Product> = emptyList()

    fun setData(products: List<Product>) {
        productList = products
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProductBinding.inflate(inflater, parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    class ProductViewHolder(private val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(product: Product) {
            binding.productNameTextView.text = product.title
            binding.productPriceTextView.text = "$${product.price}"
            binding.productDescriptionTextView.text = product.description
            binding.productRatingBar.rating = product.rating.rate.toFloat()
            binding.productRatingCountTextView.text = "(${product.rating.count} reviews)"

            // Load product image using Glide library
            Glide.with(binding.productImageView)
                .load(product.image)
                .centerCrop()
                .placeholder(R.drawable.ic_cart) // Placeholder image while loading
                .into(binding.productImageView)
        }
    }
}
