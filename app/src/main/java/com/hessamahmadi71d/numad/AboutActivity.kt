package com.hessamahmadi71d.numad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView

class AboutActivity : AppCompatActivity() {

    lateinit var imgBack: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        imgBack = findViewById(R.id.img_back)
        imgBack.setOnClickListener(View.OnClickListener { finish() })
    }

    fun rate(view: View?) {
        G.goToAppPage(this)
    }

    fun otherApps(view: View?) {
        G.otherApps(this)
    }

    fun share(view: View?) {
        G.shareApp(this)
    }



}