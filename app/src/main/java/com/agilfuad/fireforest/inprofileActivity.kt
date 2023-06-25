package com.agilfuad.fireforest

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import com.agilfuad.fireforest.databinding.ActivityInprofileBinding
import com.agilfuad.fireforest.databinding.ActivityLoginBinding
import com.bumptech.glide.Glide

class inprofileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInprofileBinding
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInprofileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val back = binding.backLogin
        back.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        editor = sharedPreferences.edit()
        val isLogin = sharedPreferences.getString("isLogin", "false")
        val nama = sharedPreferences.getString("nama", "")
        val email = sharedPreferences.getString("email", "")
        val password = sharedPreferences.getString("password", "")
        val premium = sharedPreferences.getInt("premium", 0)
        val avatar_url = sharedPreferences.getString("avatar", "")
        setView(nama,email,avatar_url)

    }
    private fun setView(nama: String? ="",email: String? = "",avatar_url: String? = ""){
        val TVnama = binding.namaInprofile
        val TVemail = binding.emailInprofile
        val IVavatar = binding.avatarIview

        TVnama.text = nama
        TVemail.text = email
        Glide.with(this)
            .load(avatar_url) // image url
            .placeholder(R.drawable.profile_default_foreground) // any placeholder to load at start
            .error(R.drawable.round_default_profile)  // any image in case of error
            .override(128, 128) // resizing
            .circleCrop()
            .into(IVavatar);  // imageview object

    }
}