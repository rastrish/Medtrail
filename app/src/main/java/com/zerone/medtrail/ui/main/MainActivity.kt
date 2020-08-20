package com.zerone.medtrail.ui.main

import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.Display
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.InputMethodManager
import android.widget.AbsListView
import androidx.lifecycle.Observer
import com.zerone.medtrail.R
import com.zerone.medtrail.ui.base.BaseActivity
import com.zerone.medtrail.util.Status
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject


class MainActivity : BaseActivity<MainViewModel>(
    MainViewModel::class
) {

    companion object {
        const val GRID_VIEW_STATE = "gridviewState"
        const val TEXT_VIEW_STATE = "textviewState"
        const val PAGE_COUNTS = "pagecount"
        const val TAG = "tag"
    }

    private var tag = String()
    private var PAGE_COUNT = 1
    private var newWidth: Int = 0
    private var isLoading: Boolean = false

    // using Koin
    private val photoAdapter: PhotoAdapter by inject()

    override fun provideLayoutId(): Int =
        R.layout.activity_main

    override fun setupView(savedInstanceState: Bundle?) {

        if (savedInstanceState != null) {
            PAGE_COUNT = savedInstanceState.getInt(PAGE_COUNTS)
            gridView.visibility = savedInstanceState.getInt(GRID_VIEW_STATE)
            textView.visibility = savedInstanceState.getInt(TEXT_VIEW_STATE)
            tag = TAG
        }

        // To get Screen width
        val display: Display = this.windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        newWidth = size.x / 2

        gridView.adapter = photoAdapter

        imageButton.setOnClickListener {

            // to hide the keypad
            try {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            } catch (e: Exception) { }


            viewModel.temp.clear()
            tag = editTextTextPersonName.text.toString()
            viewModel.getPhotos(tag, PAGE_COUNT.toString())
            textView.visibility = GONE
            gridView.visibility = VISIBLE

        }
        viewModel.list.observe(this, Observer {
            photoAdapter.submitItem(it, newWidth)
            photoAdapter.notifyDataSetChanged()
            isLoading = false
        })

        gridView.setOnScrollListener(object : AbsListView.OnScrollListener {
            override fun onScrollStateChanged(absListView: AbsListView, i: Int) {}
            override fun onScroll(
                absListView: AbsListView,
                firstVisibleItem: Int,
                visibleItemCount: Int,
                totalItemCount: Int
            ) {
                if (totalItemCount > 0) {
                    val lastVisibleItem = firstVisibleItem + visibleItemCount
                    if (!isLoading && lastVisibleItem == totalItemCount - 4) {
                        isLoading = true
                        PAGE_COUNT++
                        viewModel.getPhotos(tag, PAGE_COUNT.toString())
                    }
                }
            }
        })


    }

    override fun setupObservers() {
        super.setupObservers()
        viewModel.photos.observe(this, Observer {
            when (it.status) {
                Status.LOADING -> {
                    progressBar.visibility = VISIBLE
                }
                Status.ERROR -> {
                    progressBar.visibility = GONE
                }
                Status.SUCCESS -> {
                    progressBar.visibility = GONE
                }
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(PAGE_COUNTS, PAGE_COUNT)
        outState.putString(TAG, tag)
        outState.putInt(GRID_VIEW_STATE, gridView.visibility)
        outState.putInt(TEXT_VIEW_STATE, textView.visibility)
        super.onSaveInstanceState(outState)

    }


}