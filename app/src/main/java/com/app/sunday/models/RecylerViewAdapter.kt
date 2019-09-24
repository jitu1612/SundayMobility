package com.app.sunday.models

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.sunday.R
import com.app.sunday.views.FullActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import androidx.core.app.ActivityCompat.startActivityForResult
import android.app.Activity
import com.app.sunday.views.MainActivity

//import android.R



class RecylerViewAdapter (val context: Context,val activity: MainActivity, var newsList: ArrayList<User>) : RecyclerView.Adapter<RecylerViewAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.single,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.tvtitle.text = newsList.get(position).login
        holder.tvtype.text = newsList.get(position).type
        Glide.with(context).load(newsList.get(position).avatarUrl)
            .apply(RequestOptions().centerCrop().placeholder(com.app.sunday.R.drawable.man))
            .into(holder.image)
        holder.image.setOnClickListener {

            var gson =  Gson()
            val i = Intent(context, FullActivity::class.java)
            i.putExtra("obj", gson.toJson(newsList.get(position)))
            i.putExtra("position", position)
            val origin = activity as MainActivity
            origin.startActivityForResult(i, 1)
        }
    }

    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        val tvtitle: TextView = itemView!!.findViewById(R.id.tvtitle)
        val tvtype: TextView = itemView!!.findViewById(R.id.tvtype)
        val image: ImageView = itemView!!.findViewById(R.id.image)

    }




}