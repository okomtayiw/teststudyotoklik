package com.otoklixwiyatmoko.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.otoklixwiyatmoko.R
import com.otoklixwiyatmoko.model.Content
import com.otoklixwiyatmoko.util.BaseActivity
import com.otoklixwiyatmoko.viewmodels.ViewModelBlogPost
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_create_blog.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ActivityCreateBlog : BaseActivity() {

    private val viewModelBlogPost: ViewModelBlogPost by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_blog)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Post New Content"

        val idBlogEdit = intent.getIntExtra("id", 0)
        val titleBlogEdit = intent.getStringExtra("title")
        val contentBlogEdit = intent.getStringExtra("content")

        if(idBlogEdit != null && titleBlogEdit != null &&  contentBlogEdit != null ) {
            editData(idBlogEdit,titleBlogEdit,contentBlogEdit)
        }

        btn_save.setOnClickListener {
            if (edt_title.text.toString().isNotEmpty() && edt_content.text.toString()
                    .isNotEmpty()
            ) {

                if (idBlogEdit != null && titleBlogEdit != null &&  contentBlogEdit != null) {
                    lifecycleScope.launch {
                       observeUpdateModel(idBlogEdit)
                    }
                } else {
                    lifecycleScope.launch {
                        observeViewModel()
                    }
                }
            } else {
                showErrorSnackBar("Please data not null", true)
            }
        }
    }

    private suspend fun observeViewModel() {
        val title = edt_title.text.toString()
        val content = edt_content.text.toString()
        viewModelBlogPost.postBlog(Content(title, content))
        viewModelBlogPost.dataContent.observe(this) {
            if (it.data?.id != null) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }

    }

    private suspend fun observeUpdateModel(id: Int){
        val title = edt_title.text.toString()
        val content = edt_content.text.toString()
        viewModelBlogPost.updateBlogItem(id, Content(title,content))
        viewModelBlogPost.dataContent.observe(this) {
            if (it.data?.id != null) {
                setResult(Activity.RESULT_OK)
                finish()
            }
        }
    }

    private fun editData(id: Int, title: String, content: String){
        edt_title.setText(title)
        edt_content.setText(content)
    }
}