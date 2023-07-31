package com.sun.emarket.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.viewpager2.widget.ViewPager2
import com.sun.emarket.R
import com.sun.emarket.adapter.ImageSliderAdapter
import java.util.*
import kotlin.concurrent.scheduleAtFixedRate

class HomeFragment : Fragment() {
    private lateinit var viewPager: ViewPager2
    private lateinit var imageSliderAdapter: ImageSliderAdapter
    private val imageList = listOf(
        R.drawable.image1,
        R.drawable.image2,
        R.drawable.image3,
        R.drawable.image4,
        R.drawable.image5,
        R.drawable.image6
        // Add more image resource IDs here
    )
    private var currentPage = 0
    private val DELAY_MS: Long = 500 // Delay in milliseconds before the image slider starts auto-scrolling
    private val PERIOD_MS: Long = 3000 // Time in milliseconds between each auto-scroll
    private val timer = Timer()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        // Initialize views
        viewPager = rootView.findViewById(R.id.imageSlider)
        val dotsContainer = rootView.findViewById<LinearLayout>(R.id.dotsContainer)

        imageSliderAdapter = ImageSliderAdapter(imageList)
        viewPager.adapter = imageSliderAdapter

        // Clear existing dots before adding new ones
        dotsContainer.removeAllViews()
        createDotIndicators(dotsContainer, imageList.size)

        // Set up automatic image slider
        timer.scheduleAtFixedRate(DELAY_MS, PERIOD_MS) {
            activity?.runOnUiThread {
                if (currentPage == imageList.size) {
                    currentPage = 0
                }
                updateDotIndicators(dotsContainer, currentPage)
                viewPager.setCurrentItem(currentPage++, true)
            }
        }

        return rootView
    }

    private fun createDotIndicators(container: LinearLayout, itemCount: Int) {
        for (i in 0 until itemCount) {
            val dot = View(context)
            dot.setBackgroundResource(R.drawable.indicator_dot_unselected)

            // Set the dot size
            val dotSize = resources.getDimensionPixelSize(R.dimen.dot_size)
            val params = LinearLayout.LayoutParams(dotSize, dotSize)

            // Set margins between dots
            val margin = resources.getDimensionPixelSize(R.dimen.dot_margin)
            params.setMargins(margin, 0, margin, 0)

            dot.layoutParams = params

            container.addView(dot)
        }
    }

    private fun updateDotIndicators(container: LinearLayout, selectedPosition: Int) {
        for (i in 0 until container.childCount) {
            val dot = container.getChildAt(i)
            val drawableId =
                if (i == selectedPosition) R.drawable.indicator_dot_selected else R.drawable.indicator_dot_unselected
            dot.setBackgroundResource(drawableId)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        timer.cancel() // Cancel the timer to avoid leaks when the fragment is destroyed
    }
}

