package com.agilfuad.fireforest

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.view.WindowInsets
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val editor = sharedPreferences.edit()
        editor.clear().apply()
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        val faded = AnimationUtils.loadAnimation(this, R.anim.fades_anim)
        val boing = AnimationUtils.loadAnimation(this, R.anim.boing)
        val imageView = findViewById<ImageView>(R.id.imageView)
        val text1 = findViewById<TextView>(R.id.tv_appname)
        imageView.startAnimation(boing)
        text1.startAnimation(faded)


        val r = Runnable {
            startActivity(Intent(this@SplashActivity,DashboardActivity::class.java))
            finish()
        }
        Handler().postDelayed(r, 2050)
    }
}