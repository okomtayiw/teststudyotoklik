package com.otoklixwiyatmoko.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.otoklixwiyatmoko.R
import com.otoklixwiyatmoko.activities.ActivityCreateBlog
import com.otoklixwiyatmoko.activities.MainActivity
import com.otoklixwiyatmoko.model.BlogsItem
import kotlinx.android.synthetic.main.item_blog.view.*

class AdapterContent(
    private val context: Context,
    private var list: ArrayList<BlogsItem>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var onClickListener: OnClickListener? = null

    interface OnClickListener {
        fun onClick(position: Int, model: BlogsItem)

    }


    fun setOnclicklistener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_blog,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]
        if (holder is MyViewHolder) {
            holder.itemView.tvArea.text = model.title
            holder.itemView.tvDescription.text = Html.fromHtml(model.content)
            holder.itemView.setOnClickListener {
                if (onClickListener != null) {
                    onClickListener!!.onClick(position, model)

                }
            }

        }
    }

    fun notifyEditItem(activity: Activity, position: Int, requestCode: Int) {
        val intent = Intent(context, ActivityCreateBlog::class.java)
        intent.putExtra( "id", list[position].id)
        intent.putExtra("title", list[position].title)
        intent.putExtra("content", list[position].content)
        activity.startActivityForResult(
            intent,
            requestCode
        ) // Activity is started with requestCode
        notifyItemChanged(position)
    }

    fun removeAt(position: Int) {
        val model = list[position]
        if(context is MainActivity){ context.deleteBlogItem(model) }
        list.removeAt(position)
        notifyItemRemoved(position)
    }



    override fun getItemCount(): Int {
        return list.size
    }


    private class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}

