package com.otoklixwiyatmoko.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.otoklixwiyatmoko.R
import com.otoklixwiyatmoko.adapter.AdapterContent
import com.otoklixwiyatmoko.model.Content
import com.otoklixwiyatmoko.model.Result
import com.otoklixwiyatmoko.model.BlogsItem
import com.otoklixwiyatmoko.util.SwipeToDeleteCallback
import com.otoklixwiyatmoko.util.SwipeToEditCallback
import com.otoklixwiyatmoko.viewmodels.ContentViewModels
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

        post_blog.setOnClickListener {
            val intent = Intent(this, ActivityCreateBlog::class.java)
            startActivity(intent)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == EDIT_BLOG_POST_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                lifecycleScope.launch {
                    observeViewModel()
                }
            } else {
                Log.e("activity", "Canceled on Back Pressed")
            }
        }
    }


    private fun postValue(result: Result?) {
        if (result != null) {
            recycle_content_list.layoutManager = LinearLayoutManager(this)
            recycle_content_list.setHasFixedSize(true)

            val adapter = AdapterContent(this, result)
            recycle_content_list.adapter = adapter

            adapter.setOnclicklistener(object : AdapterContent.OnClickListener {
                override fun onClick(position: Int, model: BlogsItem) {
                    val intent = Intent(
                        this@MainActivity,
                        ActivityBlogDetail::class.java
                    )
                    intent.putExtra(EXTRA_BLOG_ID, model.id)
                    startActivity(intent)
                }

            })

            val editSwipeHandler = object : SwipeToEditCallback(this) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val adapter = recycle_content_list.adapter as AdapterContent
                    adapter.notifyEditItem(
                        this@MainActivity,
                        viewHolder.adapterPosition,
                        EDIT_BLOG_POST_REQUEST_CODE
                    )
                }

            }

            val editItemTouchHelper = ItemTouchHelper(editSwipeHandler)
            editItemTouchHelper.attachToRecyclerView(recycle_content_list)


            val deleteSwipeHandler = object : SwipeToDeleteCallback(this) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val builder = AlertDialog.Builder(this@MainActivity)
                    builder.setMessage("Delete").setCancelable(false)
                        .setPositiveButton("yes") { _, _ ->
                            val adapter = recycle_content_list.adapter as AdapterContent
                            adapter.removeAt(viewHolder.adapterPosition)

                        }
                        .setNegativeButton("Cancel") { dialog, _ ->
                            lifecycleScope.launch {
                                loadDataContent()
                            }
                            dialog.dismiss()
                        }
                    val alert = builder.create()
                    alert.setCancelable(false)
                    alert.show()

                }
            }
            val deleteItemTouchHelper = ItemTouchHelper(deleteSwipeHandler)
            deleteItemTouchHelper.attachToRecyclerView(recycle_content_list)


        }
    }

    fun deleteBlogItem(model: BlogsItem) {

        lifecycleScope.launch {
            contentViewModels.deleteBlog(model.id, Content(model.title, model.content))
        }

    }


    companion object {
        internal const val EXTRA_BLOG_ID = "extra_blog_item_id"
        const val EDIT_BLOG_POST_REQUEST_CODE = 2
    }
}