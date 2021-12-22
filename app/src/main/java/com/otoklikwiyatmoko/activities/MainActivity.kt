package com.otoklikwiyatmoko.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.otoklikwiyatmoko.R
import com.otoklikwiyatmoko.adapter.AdapterContent
import com.otoklikwiyatmoko.model.Result
import com.otoklikwiyatmoko.model.blogsItem
import com.otoklikwiyatmoko.viewmodels.ContentViewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val contentViewModels: ContentViewModels by viewModels()
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mSwipeRefreshLayout = swipeRefreshLayout
        lifecycleScope.launch {
            loadDataContent()
        }

    }

    private suspend fun loadDataContent() {
        observeViewModel()

        mSwipeRefreshLayout.setOnRefreshListener {
            lifecycleScope.launch {
                observeViewModel()
            }
        }
    }

    private suspend fun observeViewModel() {
        contentViewModels.getBlogs()
        contentViewModels.dataContent.observe(this) {

            postValue(it.data)
            mSwipeRefreshLayout.isRefreshing = false

        }

    }

    private fun postValue(result: Result?) {
        if (result != null) {
            recycle_content_list.layoutManager = LinearLayoutManager(this)
            recycle_content_list.setHasFixedSize(true)

            val adapter = AdapterContent(this, result)
            recycle_content_list.adapter = adapter

            adapter.setOnclicklistener(object : AdapterContent.OnClickListener{
                override fun onClick(position: Int, model: blogsItem) {
//                    Toast.makeText(this@MainActivity, model.id.toString(), Toast.LENGTH_SHORT).show()
                    val intent = Intent(
                        this@MainActivity,
                        ActivityBlogDetail::class.java
                    )
                    intent.putExtra(EXTRA_BLOG_ID, model.id)
                    startActivity(intent)
                }

            })

        }
    }

    companion object {
        internal const val EXTRA_BLOG_ID = "extra_blog_item_id"
    }
}