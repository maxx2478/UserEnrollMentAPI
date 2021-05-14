package com.exotic.myapplication.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.exotic.myapplication.R
import com.exotic.myapplication.model.ResponseModels.RegisterResponseModel
import com.exotic.myapplication.model.AllUsers.UserData
import com.exotic.myapplication.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private  val apiService = ApiService()
    private var name:EditText?=null
    private var email:EditText?=null
    private var phno:EditText?=null
    private var password:EditText?=null
    private var uuid:EditText?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeViews()

    }

    private fun initializeViews() {
        name = findViewById(R.id.name)
        email = findViewById(R.id.email)
        phno = findViewById(R.id.phonenumber)
        password = findViewById(R.id.password)
        uuid = findViewById(R.id.uuid)

    }


    override fun onStart() {
        super.onStart()


    }



    fun getuserdata(name:String, email:String, phone_number:String,
    pass:String, uuid:String): Map<String, String> // create a map of user input data
    {
        return mapOf<String, String>("name" to name, "email" to email, "phone_number" to phone_number, "password" to pass
                , "uuid" to uuid)
    }

    fun createUser()
    {
        val request = apiService.getApiInstance()
        val JsonObject  = getuserdata(name!!.text.toString(), email!!.text.toString(),
        phno!!.text.toString(), password!!.text.toString(), uuid!!.text.toString())
        val createusercall = request.createUser(JsonObject)
        createusercall.enqueue(object : Callback<RegisterResponseModel> {
            override fun onResponse(call: Call<RegisterResponseModel>, response: Response<RegisterResponseModel>) {
                if (response.isSuccessful) {

                    Toast.makeText(this@MainActivity, "Added user", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@MainActivity, HomeScreen::class.java))
                    finish()

                } else {
                    Toast.makeText(this@MainActivity, response.code().toString() + " " + response.message(), Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<RegisterResponseModel>, t: Throwable) {

                t.printStackTrace()

            }

        })
    }


    fun signin(view: View) {
        createUser()
    }


}