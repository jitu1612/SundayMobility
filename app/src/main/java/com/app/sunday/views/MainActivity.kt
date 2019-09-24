package com.app.sunday.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.app.sunday.R
import com.app.sunday.api.NetworkCalls
import com.app.sunday.databinding.ActivityMainBinding
import com.app.sunday.models.RecylerViewAdapter
import com.app.sunday.models.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.content.Intent
//import android.R
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    private var adapter: RecylerViewAdapter? = null
    lateinit var binding: ActivityMainBinding
    var jsonResponse = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.fabadd.setOnClickListener { add() }

        getrewsItem()
    }

    fun getrewsItem() {

        val retrofit = Retrofit.Builder()
            .baseUrl(NetworkCalls.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(NetworkCalls::class.java)

        var call = api.getData() as Call<ArrayList<User>>

        call.enqueue(object : Callback<ArrayList<User>> {
            override fun onResponse(
                call: Call<ArrayList<User>>,
                response: Response<ArrayList<User>>
            ) {
                Log.d("LogdgetTitle: ", response.body().toString())
                Log.d("Logdresponse.code(): ", response.toString())
                if (response.code() == 200) {
                    Log.d("Logdresponse.200): ", "")
                    jsonResponse = response.body() as ArrayList<User>

                    adapter =
                        RecylerViewAdapter(applicationContext, this@MainActivity, jsonResponse);
                    binding.recyclerview?.layoutManager = GridLayoutManager(applicationContext, 2)
                    binding.recyclerview?.adapter = adapter
                    binding.recyclerview?.setAdapter(adapter)
                    Log.d("Logdresponse.200): ", jsonResponse?.size.toString())
                } else {
                    Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
                }

            }

            override
            fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                Toast.makeText(applicationContext, t.toString(), Toast.LENGTH_SHORT).show()
                Log.d("LogdThrowable: ", t.toString())
            }
        })
    }

    fun add() {

        var u = User("New User", "lol", "User")

        jsonResponse.add(u)
        adapter?.notifyDataSetChanged()
        binding.recyclerview.scrollToPosition(jsonResponse.size - 1)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("requestCode: ", "onActivityResult")

        try {
            if (requestCode == 1) {

                Log.d("requestCode: ", "" + requestCode)
                Log.d("requestCode: ", "" + data?.getIntExtra("position",0))
                jsonResponse.removeAt(data?.getIntExtra("position",0)?.toInt()!!)

                adapter?.notifyDataSetChanged()
            }
        } catch (e: Exception) {
            e.toString()
        }

    }
}

/*

    Display splash screen for 2 seconds
    API URL: https://api.github.com/users
    Hit this web service and present this data in recycler view /grid view and add an option to add a new item in the list
    List row should have image and data
    After clicking on the image the second screen will display a large image view (with zoom out functionality)
    On the second screen, there should be an option for delete row

    Show the following

    Image
    User name
    User Type
    Note: For image use key "avatar_url"

    For user name use key "login"
*/




