package com.exotic.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.exotic.myapplication.R
import com.exotic.myapplication.model.AllUsers.Data
import com.exotic.myapplication.model.PostModels.Users

class AllUsersAdapter: RecyclerView.Adapter<allusersViewHolder>
{

    var list: ArrayList<Data>?=null

    constructor(list:ArrayList<Data>)
    {
        this.list = list
    }

    fun updateData(list:ArrayList<Data>)
    {
        this.list!!.clear()
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): allusersViewHolder {
        var view:View?=null
        view = LayoutInflater.from(parent.context).inflate(R.layout.single_user,parent, false)
        return allusersViewHolder(view!!)
    }

    override fun onBindViewHolder(holder: allusersViewHolder, position: Int) {

        val users:Data = list!![position]
        holder.name.text = users.name
        holder.email.text = users.email
        holder.phno.text = users.phone_number

    }

    override fun getItemCount(): Int {
        return list!!.size
    }


}

class allusersViewHolder(view: View): RecyclerView.ViewHolder(view)
{

    var name = view.findViewById<TextView>(R.id.name)
    var email = view.findViewById<TextView>(R.id.email)
    var phno = view.findViewById<TextView>(R.id.phonenumber)


}