package com.app.sunday.views

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.app.sunday.R
import com.app.sunday.databinding.ActivityFullBinding
import com.app.sunday.models.RecylerViewAdapter
import com.app.sunday.models.User
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson

class FullActivity : AppCompatActivity() {

    lateinit var binding: ActivityFullBinding
    //var position : Int
    private var position: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_full)


        val intent = intent

        if (intent.hasExtra("obj")) {

            try {

                val gson = Gson()
                val strObj = getIntent().getStringExtra("obj")
                position = getIntent().getIntExtra("position", 0)
                val cat = gson.fromJson<User>(strObj, User::class.java)
                //binding.tvcontent.text = cat.products.get(0).products?.get(0)
                binding.tvtitle.text = cat.login
                binding.tvtype.text = cat.type

                Glide.with(applicationContext).load(cat.avatarUrl)
                    .apply(RequestOptions().placeholder(com.app.sunday.R.drawable.man))
                    .into(binding.myZoomageView)

            } catch (e: Exception) {
                e.toString()
            }

            binding.fabAddTopic.setOnClickListener {

                val dialogBuilder = AlertDialog.Builder(this)

                dialogBuilder.setMessage("Do you really want to this user?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id ->
                        val result = Intent()
                        result.putExtra("position", position)
                        setResult(1, result)

                        finish()
                    })
                    .setNegativeButton("No", DialogInterface.OnClickListener { dialog, id ->
                        dialog.cancel()
                    })

                val alert = dialogBuilder.create()
                //alert.setTitle("AlertDialogExample")
                alert.show()


            }

        }

    }


}
