package ro.dragosivanov.ui

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import ro.dragosivanov.R

abstract class BaseFragment(@LayoutRes layoutId: Int) : Fragment(layoutId) {

    private lateinit var loadingProgressBar: ProgressBar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingProgressBar = requireActivity().findViewById<ProgressBar>(R.id.loading_bar)
    }

    fun showLoading() {
        loadingProgressBar.visibility = View.VISIBLE
    }

    fun hideLoading() {
        loadingProgressBar.visibility = View.GONE
    }
}