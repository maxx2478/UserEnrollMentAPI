package com.exotic.myapplication.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.exotic.myapplication.R
import com.exotic.myapplication.model.UserData
import com.exotic.myapplication.model.Users
import com.exotic.myapplication.network.ApiService
import com.exotic.myapplication.network.UsersApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {
    private  val apiService = ApiService()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }


    override fun onStart() {
        super.onStart()

        val request = apiService.getApiInstance()
        val call = request.getAllUsers()
        call.enqueue(object : Callback<UserData> {
            override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@MainActivity, "Success, Total Users are: " + response.body()!!.data.size.toString() , Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<UserData>, t: Throwable) {

                t.printStackTrace()

            }

        })


        val user = Users("1x", "manohar patil", " mkpatil24@gmail.com", "9345353464",
        "ndstj436")

        val createusercall = request.createUser(user)
        createusercall.enqueue(object : Callback<Users> {
            override fun onResponse(call: Call<Users>, response: Response<Users>) {
                if (response.isSuccessful) {

                    Toast.makeText(this@MainActivity, "Added user", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(this@MainActivity, response.code().toString(), Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<Users>, t: Throwable) {

                t.printStackTrace()

            }

        })



    }

}