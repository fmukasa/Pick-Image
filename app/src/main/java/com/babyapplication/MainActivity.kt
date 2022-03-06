package com.babyapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.babyapplication.view.ItemsActivity
import com.babyapplication.view.UploadActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btnViewAll.setOnClickListener {
            startActivity(Intent(this, ItemsActivity::class.java))
        }
        btnAddInfo.setOnClickListener {
            startActivity(Intent(this, UploadActivity::class.java))
        }
    }
}