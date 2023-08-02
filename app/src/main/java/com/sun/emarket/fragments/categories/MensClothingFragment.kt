package com.sun.emarket.fragments.categories


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.sun.emarket.R
class MensClothingFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_mens_clothing, container, false)

        val category = arguments?.getString("category").toString()
        recyclerView = rootView.findViewById(R.id.productRecyclerView)
        progressBar = rootView.findViewById(R.id.progressBar)

        CategoriesHelper.fetchData(category,recyclerView,progressBar,requireContext())

        return rootView
    }
}
