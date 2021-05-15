package com.exotic.myapplication.views

import `in`.mayanknagwanshi.imagepicker.ImageSelectActivity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.exotic.myapplication.R
import com.exotic.myapplication.adapter.AllUsersAdapter
import com.exotic.myapplication.model.AllUsers.UserData
import com.exotic.myapplication.model.ResponseModels.User
import com.exotic.myapplication.network.ApiService
import com.exotic.myapplication.utils.SessionManager
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class HomeScreen : AppCompatActivity() {

    private  val apiService = ApiService()
    private var recyclerView: RecyclerView?=null
    lateinit var adapterx: AllUsersAdapter
    private var name: TextView?=null
    private var email: TextView?=null
    private var phno: TextView?=null
    private var profilepic: ImageView?=null

    private var sessionManager: SharedPreferences?=null
    private var imagePath:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)
        val builder = VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        initializeViews()
        setCurrentUserData()
        adapterx = AllUsersAdapter(arrayListOf())
        recyclerView!!.adapter = adapterx
        recyclerView!!.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        fetchAllUsers()

    }

    private fun initializeViews()
    {
        profilepic = findViewById(R.id.profile)
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

    fun changeimage(view: View) {

        val intent = Intent(this@HomeScreen, ImageSelectActivity::class.java)
        intent.putExtra(ImageSelectActivity.FLAG_COMPRESS, false) //default is true
        intent.putExtra(ImageSelectActivity.FLAG_CAMERA, false) //default is true
        intent.putExtra(ImageSelectActivity.FLAG_GALLERY, true) //default is true

        startActivityForResult(intent, 1)


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

         if (requestCode == 1 && resultCode == RESULT_OK) {
            imagePath = data!!.getStringExtra(ImageSelectActivity.RESULT_FILE_PATH)
            val selectedImage = BitmapFactory.decodeFile(imagePath)
            profilepic!!.setImageBitmap(selectedImage)
             Toast.makeText(this@HomeScreen, "Image Loaded", Toast.LENGTH_SHORT).show()

             uploadtoAws()
        }


    }

     fun uploadtoAws() {

         val realfilex = File(imagePath!!)
         val requestBody = RequestBody.create(MediaType.parse("image/*"), realfilex)
         val body = MultipartBody.Part.createFormData("image", realfilex.name, requestBody)


         val request2 = apiService.getApiInstance()
         val call = request2.uploadimage(body)
         call.enqueue(object : Callback<String> {
             override fun onResponse(call: Call<String>, response: Response<String>) {

                 if (response.isSuccessful) {
                     Toast.makeText(this@HomeScreen, response.body(), Toast.LENGTH_SHORT).show()
                     //response.body() is the image url we got from aws.

                 } else {
                     Toast.makeText(this@HomeScreen, response.code().toString(), Toast.LENGTH_SHORT).show()
                 }

             }

             override fun onFailure(call: Call<String>, t: Throwable) {
                 t.printStackTrace()
             }

         })

    }


}