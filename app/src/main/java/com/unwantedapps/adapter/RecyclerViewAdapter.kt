package com.unwantedapps.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.unwantedapps.R
import com.unwantedapps.model.ImageUploadInfo

 class RecyclerViewAdapter(
    var context: Context,
    var MainImageUploadInfoList: MutableList<ImageUploadInfo?>
) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_items, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val UploadInfo = MainImageUploadInfoList[position]
        holder.appName.text = UploadInfo!!.appName
        holder.appNameDesc.text = UploadInfo.appDesc
        holder.appNameackage.text = UploadInfo.appPackage


        //Loading image from Glide library.
        Log.e("TAG","Image Url==> "+UploadInfo.imageURL)
        Glide.with(context).load(UploadInfo.imageURL).into(holder.appNameImage)
    }

    override fun getItemCount(): Int {
        return MainImageUploadInfoList.size
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var appNameImage: ImageView = itemView.findViewById<View>(R.id.imageView) as ImageView
        var appName: TextView = itemView.findViewById<View>(R.id.ImageNameTextView) as TextView
        var appNameDesc: TextView = itemView.findViewById<View>(R.id.tv_set_desc) as TextView
        var appNameackage: TextView = itemView.findViewById<View>(R.id.tv_set_package) as TextView

    }

}