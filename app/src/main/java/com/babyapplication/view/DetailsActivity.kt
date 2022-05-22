package com.babyapplication.view


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.babyapplication.R
import com.babyapplication.model.Teacher
import com.babyapplication.uitel.loadImage
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import okhttp3.Cache.key

class DetailsActivity : AppCompatActivity() {

  //  private lateinit var deleteBtn : Button
  private var mDatabaseRef: DatabaseReference? = null
    private var mRefDatabase: DatabaseReference? = null
    private var mStorageRef : StorageReference? = null


    private lateinit var teacherDetailImageView: ImageView
    private lateinit var nameDetailTextView : TextView
    private lateinit var descriptionDetailTextView :TextView
    private lateinit var editBtn : TextView
    private lateinit var deleteBtn: TextView
    private lateinit var textView: TextView
    private lateinit var ratingBar: RatingBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        teacherDetailImageView =findViewById(R.id.teacherDetailImageView)
        nameDetailTextView = findViewById(R.id.nameDetailTextView)
        descriptionDetailTextView = findViewById(R.id.descriptionDetailTextView)
        editBtn = findViewById(R.id.editBtn)
        deleteBtn = findViewById(R.id.deleteBtn)
        textView =findViewById(R.id.textView)
        ratingBar =findViewById(R.id.ratingBar)

        val intss = intent
        val nameT = intss.getStringExtra("NAMET")
        val desT = intss.getStringExtra("DESCRIT")
        val imgT = intss.getStringExtra("IMGURI")

        nameDetailTextView.text = nameT
        descriptionDetailTextView.text = desT
        teacherDetailImageView.loadImage(imgT)

        mRefDatabase = FirebaseDatabase.getInstance().reference.child("Teacher")
        mDatabaseRef = FirebaseDatabase.getInstance().reference.child("").child( "")
        mStorageRef = FirebaseStorage.getInstance().getReference("TeacherImage")


        deleteBtn.setOnClickListener(View.OnClickListener {
            mDatabaseRef!!.removeValue().addOnSuccessListener(OnSuccessListener<Void?> {
                mStorageRef!!.delete().addOnSuccessListener(OnSuccessListener<Void?> {
                   // mRefDatabase!!.delete().addOnSuccessListener ( OnSuccessListener<Void?> {

                        startActivity(Intent(applicationContext, ItemsActivity::class.java
                        )
                        )
                    } )

                    })
            })
       // })
    }

    }
