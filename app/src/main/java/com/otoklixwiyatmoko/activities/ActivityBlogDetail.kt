package com.otoklixwiyatmoko.activities

import android.os.Bundle
import android.text.Html
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.otoklixwiyatmoko.R
import com.otoklixwiyatmoko.util.BaseActivity
import com.otoklixwiyatmoko.viewmodels.ViewModelBlogDetail
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_blog_detail.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ActivityBlogDetail : BaseActivity() {

    private val viewModelBlogDetail: ViewModelBlogDetail by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blog_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Show a Post"

        val idBlogDetail = intent.getIntExtra("extra_blog_item_id", 0)
        if(idBlogDetail != null) {
            lifecycleScope.launch {
                observeViewModel(idBlogDetail)
            }
        }

    }

    private suspend fun observeViewModel(id: Int) {
        viewModelBlogDetail.getBlogsId(id)
        viewModelBlogDetail.dataContent.observe(this) {
            tv_title.text = it.data?.title
            tv_content.text = Html.fromHtml(it.data?.content)
        }

    }
}