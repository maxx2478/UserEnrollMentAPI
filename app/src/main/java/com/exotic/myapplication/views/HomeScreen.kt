package com.exotic.myapplication.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.exotic.myapplication.R
import com.exotic.myapplication.adapter.AllUsersAdapter
import com.exotic.myapplication.model.AllUsers.UserData
import com.exotic.myapplication.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeScreen : AppCompatActivity() {

    private  val apiService = ApiService()
    private var recyclerView: RecyclerView?=null
    lateinit var adapterx: AllUsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        recyclerView = findViewById(R.id.recyclerview)
        adapterx = AllUsersAdapter(arrayListOf())
        recyclerView!!.adapter = adapterx
        recyclerView!!.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        fetchAllUsers()

    }


    fun fetchAllUsers()
    {
        val request = apiService.getApiInstance()
        val call = request.getAllUsers()
        call.enqueue(object : Callback<UserData> {
            override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                if (response.isSuccessful) {
                    // Toast.makeText(this@MainActivity, "Success, Total Users are: " + response.body()!!.data.size.toString() , Toast.LENGTH_SHORT).show()
                    adapterx.updateData(response.body()!!.data)
                } else {
                    Toast.makeText(this@HomeScreen, "Error", Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<UserData>, t: Throwable) {

                t.printStackTrace()

            }

        })

    }

    fun refreshlist(view: View) {
        recreate()
    }

    fun changeimage(view: View) {}



}