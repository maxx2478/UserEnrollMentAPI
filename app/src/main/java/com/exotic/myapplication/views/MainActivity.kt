package com.exotic.myapplication.views

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.exotic.myapplication.R
import com.exotic.myapplication.model.ResponseModels.RegisterResponseModel
import com.exotic.myapplication.network.ApiService
import com.exotic.myapplication.utils.SessionManager
import com.google.gson.Gson
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
    private var sharedPreferences:SharedPreferences?=null
    var gson:Gson?=null

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
        gson = Gson()
        sharedPreferences = SessionManager.customPrefs(this, "session")

    }


    // create a map of user input data
    private fun getUserData(name:String, email:String, phone_number:String, pass:String, uuid:String): Map<String, String>
    {
        return mapOf("name" to name, "email" to email, "phone_number" to phone_number, "password" to pass
                , "uuid" to uuid)
    }

    private fun createUser()
    {
        val request = apiService.getApiInstance()
        val jsonObject  = getUserData(name!!.text.toString(), email!!.text.toString(), phno!!.text.toString(), password!!.text.toString(), uuid!!.text.toString())

        val createUserCall = request.createUser(jsonObject)
        createUserCall.enqueue(object : Callback<RegisterResponseModel> {
            override fun onResponse(call: Call<RegisterResponseModel>, response: Response<RegisterResponseModel>) {
                if (response.isSuccessful) {

                    Toast.makeText(this@MainActivity, "Added user", Toast.LENGTH_SHORT).show()

                    //Save user to Current App Session
                    val datastring = gson!!.toJson(response.body()!!.user) //convert user model to String
                    val token = response.body()!!.token
                    sharedPreferences!!.edit().putString("session", datastring).apply() //Saving Current User Data to sharedpreferences
                    sharedPreferences!!.edit().putString("token", token).apply() //saving token in a separte key
                    //start homeactivity
                    startActivity(Intent(this@MainActivity, HomeScreen::class.java))
                    finish()

                } else {
                    //an error occured
                    Toast.makeText(this@MainActivity, response.code().toString() + " " + response.message(), Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<RegisterResponseModel>, t: Throwable) {
                //failed to call server, print stack trace
                t.printStackTrace()

            }

        })
    }


    fun signIn(view: View) {
        createUser()
    }


}