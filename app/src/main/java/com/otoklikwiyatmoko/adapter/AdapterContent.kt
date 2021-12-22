package com.otoklikwiyatmoko.adapter

import android.app.Activity
import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.otoklikwiyatmoko.R
import com.otoklikwiyatmoko.model.blogsItem
import kotlinx.android.synthetic.main.item_blog.view.*

class AdapterContent(
    private val context: Context,
    private var list: ArrayList<blogsItem>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var onClickListener: OnClickListener? = null

    interface OnClickListener {
        fun onClick(position: Int, model: blogsItem)

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

    fun notifyEditItem(activity: Activity, position: Int, requestcode: Int) {
        notifyItemChanged(position)
    }



    override fun getItemCount(): Int {
        return list.size
    }


    private class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}

