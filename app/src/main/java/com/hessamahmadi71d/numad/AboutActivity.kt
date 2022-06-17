package com.hessamahmadi71d.numad

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        val imgBack: ImageView = findViewById(R.id.img_back)
        imgBack.setOnClickListener(View.OnClickListener { finish() })
        val tvVersion: TextView = findViewById(R.id.tvVersion)
        tvVersion.text = getString(R.string.version) + " " + BuildConfig.VERSION_NAME
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