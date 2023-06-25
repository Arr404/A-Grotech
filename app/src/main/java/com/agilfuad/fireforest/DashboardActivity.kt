package com.agilfuad.fireforest

import android.app.AlertDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.agilfuad.fireforest.databinding.ActivityDashboardBinding
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.nav_header_dashboard.view.*

class DashboardActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        editor = sharedPreferences.edit()
        val isLogin = sharedPreferences.getString("isLogin", "false")
        val nama = sharedPreferences.getString("nama", "")
        val email = sharedPreferences.getString("email", "")
        val avatar_url = sharedPreferences.getString("avatar", "")

        setSupportActionBar(binding.appBarDashboard.toolbar)

        binding.appBarDashboard.fab.setOnClickListener { view ->
            alert()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_dashboard)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        if(isLogin == "true"){
            val imageViewAvatar = binding.navView.getHeaderView(0).imageView
            val textViewNama = binding.navView.getHeaderView(0).nama_nav
            val textViewGmail = binding.navView.getHeaderView(0).gmail_nav

            textViewNama.text = nama
            textViewGmail.text = email
            Glide.with(this)
                .load("") // image url
                .placeholder(R.drawable.profile_default_foreground) // any placeholder to load at start
                .error(R.drawable.round_default_profile)  // any image in case of error
                .override(128, 128) // resizing
                .circleCrop()
                .into(imageViewAvatar);  // imageview object
        }




    }
    private fun alert(){
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        builder.setTitle("Tambahkan sensor baru")
        val dialogLayout = inflater.inflate(R.layout.alert_dialog_with_edittext, null)
        val editText  = dialogLayout.findViewById<EditText>(R.id.editText)
        builder.setView(dialogLayout)
        builder.setPositiveButton("OK") { dialogInterface, i -> Toast.makeText(applicationContext, "Sensor id : " + editText.text.toString(), Toast.LENGTH_SHORT).show() }
        builder.show()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_dashboard)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}