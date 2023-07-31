package com.sun.emarket.fragments

// CategoriesFragment.kt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sun.emarket.R
import com.sun.emarket.adapter.CategoryAdapter
import com.sun.emarket.api.FakeApiService
import com.sun.emarket.fragments.categories.ElectronicsFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriesFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var categoryContainer: FrameLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_categories, container, false)

        recyclerView = rootView.findViewById(R.id.categoryRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        categoryAdapter = CategoryAdapter(requireActivity().supportFragmentManager)
        recyclerView.adapter = categoryAdapter

        progressBar = rootView.findViewById(R.id.progressBar)
        categoryContainer = rootView.findViewById(R.id.categoryContainer)

        // Fetch categories from the API
        val apiService = FakeApiService.create()
        val call = apiService.getCategories()

        progressBar.visibility = View.VISIBLE
        categoryContainer.visibility = View.GONE

        call.enqueue(object : Callback<List<String>> {
            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                progressBar.visibility = View.GONE
                categoryContainer.visibility = View.VISIBLE
                if (response.isSuccessful) {
                    val categories = response.body()
                    categories?.let {
                        categoryAdapter.setData(it)
                    }
                }
                val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
                transaction.replace(R.id.categoryContainer, ElectronicsFragment())
                transaction.addToBackStack(null)
                transaction.commit()
            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                // Handle API call failure here
                progressBar.visibility = View.GONE
            }
        })

        return rootView
    }
}
