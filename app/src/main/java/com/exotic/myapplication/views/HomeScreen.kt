package com.exotic.myapplication.views

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.exotic.myapplication.R
import com.exotic.myapplication.adapter.AllUsersAdapter
import com.exotic.myapplication.model.AllUsers.UserData
import com.exotic.myapplication.model.ResponseModels.User
import com.exotic.myapplication.network.ApiService
import com.exotic.myapplication.utils.SessionManager
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeScreen : AppCompatActivity() {

    private  val apiService = ApiService()
    private var recyclerView: RecyclerView?=null
    lateinit var adapterx: AllUsersAdapter
    private var name: TextView?=null
    private var email: TextView?=null
    private var phno: TextView?=null
    private var sessionManager: SharedPreferences?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        initializeViews()
        setCurrentUserData()
        adapterx = AllUsersAdapter(arrayListOf())
        recyclerView!!.adapter = adapterx
        recyclerView!!.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        fetchAllUsers()

    }

    private fun initializeViews()
    {
        name = findViewById(R.id.name)
        email = findViewById(R.id.email)
        phno = findViewById(R.id.phonenumber)
        recyclerView = findViewById(R.id.recyclerview)
        sessionManager = SessionManager.customPrefs(this, "session")

    }

    private fun setCurrentUserData()
    {
        //String to Model Class
        val userdata = Gson().fromJson(sessionManager!!.getString("session", " "), User::class.java)
        name!!.text = userdata.name
        email!!.text = userdata.email
        phno!!.text = userdata.phone_number

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