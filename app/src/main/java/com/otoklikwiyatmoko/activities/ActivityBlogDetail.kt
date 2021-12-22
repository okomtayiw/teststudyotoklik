package com.otoklikwiyatmoko.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.otoklikwiyatmoko.R

class ActivityBlogDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blog_detail)

        val idBlogDetail = intent.getIntExtra("extra_blog_item_id", 0)
        Log.d("idne", idBlogDetail.toString())
    }
}