package com.zerone.medtrail.ui.main

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.BaseAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide


class PhotoAdapter : BaseAdapter(){

   private var imageList : ArrayList<String> = ArrayList()

    var width : Int = 0


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val imageView: ImageView
        if (convertView == null) {
            imageView = ImageView(parent!!.context)
            imageView.layoutParams = AbsListView.LayoutParams(width, 500)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView.setPadding(8, 8, 8, 8)
        } else {
            imageView =  convertView as ImageView
        }
        try {
            Glide.with(parent!!.context).load(imageList[position]).into(imageView)
        }catch (ex: Exception){
            Log.e("Exception",ex.toString())
        }
        return imageView;
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
       return imageList.size
    }

    fun submitItem(imageList : ArrayList<String>, width : Int){
        Log.e("width" , width.toString())
        this.imageList = imageList
        this.width = width
    }


}