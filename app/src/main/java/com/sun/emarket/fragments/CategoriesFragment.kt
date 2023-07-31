package com.sun.emarket.fragments

// CategoriesFragment.kt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sun.emarket.R
import com.sun.emarket.adapter.CategoryAdapter
import com.sun.emarket.api.FakeApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriesFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_categories, container, false)

        recyclerView = rootView.findViewById(R.id.categoryRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        categoryAdapter = CategoryAdapter()
        recyclerView.adapter = categoryAdapter

        // Fetch categories from the API
        val apiService = FakeApiService.create()
        val call = apiService.getCategories()

        call.enqueue(object : Callback<List<String>> {
            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                if (response.isSuccessful) {
                    val categories = response.body()
                    categories?.let {
                        categoryAdapter.setData(it)
                    }
                }
            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                // Handle API call failure here
            }
        })

        return rootView
    }
}
