package com.babyapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.babyapplication.view.ItemsActivity
import com.babyapplication.view.UploadActivity
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback


class MainActivity : AppCompatActivity() {

    private lateinit var btnViewAll : Button
    private lateinit var btnAddInfo: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btnViewAll = findViewById(R.id.btnViewAll)
        btnAddInfo = findViewById(R.id.btnAddInfo)

        btnViewAll.setOnClickListener {

            startActivity(Intent(this, ItemsActivity::class.java))
        }
        btnAddInfo.setOnClickListener {
            startActivity(Intent(this, UploadActivity::class.java))
        }

    }

}