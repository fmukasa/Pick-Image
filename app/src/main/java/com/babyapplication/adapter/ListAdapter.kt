package com.babyapplication.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.babyapplication.R
import com.babyapplication.model.Teacher
import com.babyapplication.uitel.loadImage
import com.babyapplication.view.DetailsActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.row_item.view.*

class ListAdapter (val mContext: Context, val teacherList:List<Teacher>,):
    RecyclerView.Adapter<ListAdapter.ListViewHolder>()
{
   // private var mDatabaseRef: DatabaseReference? = null

    inner class ListViewHolder(var v: View): RecyclerView.ViewHolder(v){
        val imgT = v.findViewById<ImageView>(R.id.teacherImageView)
        val nameT = v. findViewById<TextView>(R.id.nameTextView)
        val descriT = v. findViewById<TextView>(R.id.descriptionTextView)
        val seeMoreBtn = v.findViewById<TextView>(R.id.seeMoreBtn)
       // val deletePost = v.findViewById<Button>(R.id.deletePost)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.row_item,parent,false)
        return ListViewHolder(v)
    }
    override fun getItemCount(): Int = teacherList.size

    override fun onBindViewHolder(holder: ListViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val newList = teacherList[position]
        holder.nameT.text = newList.name
        holder.descriT.text = newList.description
        holder.imgT.loadImage(newList.imageUrl)

        holder.v.setOnClickListener {

            val name = newList.name
            val descrip = newList.description
            val imgUri = newList.imageUrl

            val mIntent = Intent(mContext, DetailsActivity::class.java)
            mIntent.putExtra("NAMET",name)
            mIntent.putExtra("DESCRIT",descrip)
            mIntent.putExtra("IMGURI",imgUri)
            mContext.startActivity(mIntent)

           /** holder.deletePost.setOnClickListener(object :View.OnClickListener {
                override fun onClick(v: View?) {
                    val dialog = AlertDialog.Builder(mContext)
                        .setTitle("Delete Post")
                        .create()
                 FirebaseDatabase.getInstance().reference.child("Teacher")
                     .child(getItemId(position).toString()).removeValue()
                }

            })*/

           /* holder.v.setOnClickListener {
                val view = LayoutInflater.from(mContext).inflate(R.layout.row_item,null)

                val dialog = AlertDialog.Builder(mContext)
                    .setTitle("Delete Post")
                    .create()
            }*/


           /** mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Teacher")

            holder.deletePost.setOnClickListener(object :View.OnClickListener{
                override fun onClick(v: View?) {
                    mDatabaseRef!!.child()
                }
            })*/

        }

    }

}
