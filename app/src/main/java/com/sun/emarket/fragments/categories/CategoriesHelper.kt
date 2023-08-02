package com.sun.emarket.fragments.categories

import android.content.Context
import android.content.res.ColorStateList
import android.view.View
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sun.emarket.R
import com.sun.emarket.adapter.ProductAdapter
import com.sun.emarket.api.FakeApiService
import com.sun.emarket.model.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriesHelper {
    companion object {
        private lateinit var productAdapter: ProductAdapter
        fun fetchData(category: String, recyclerView: RecyclerView, progressBar: ProgressBar, context: Context){
            recyclerView.layoutManager = GridLayoutManager(context, 2)

            productAdapter = ProductAdapter()
            recyclerView.adapter = productAdapter

            val customColor = ContextCompat.getColor(context, R.color.colorCategory1)
            progressBar.indeterminateTintList = ColorStateList.valueOf(customColor)


            // Fetch products in the Electronics category from the API
            val apiService = FakeApiService.create()
            val call = apiService.getProductsInCategory(category)

            progressBar.visibility = View.VISIBLE

            call.enqueue(object : Callback<List<Product>> {
                override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                    progressBar.visibility = View.GONE

                    if (response.isSuccessful) {
                        val products = response.body()
                        products?.let {
                            productAdapter.setData(it)
                        }
                    }
                }

                override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                    progressBar.visibility = View.GONE
                    // Handle API call failure here
                }
            })
        }
    }
}