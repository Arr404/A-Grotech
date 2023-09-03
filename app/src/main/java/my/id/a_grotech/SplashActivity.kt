package my.id.a_grotech

//import eightbitlab.com.blurview.RenderScriptBlur

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.view.WindowInsets
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.github.furkankaplan.fkblurview.FKBlurView
import my.id.a_grotech.`object`.UserPreference
import my.id.a_grotech.databinding.ActivitySplashBinding


class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private lateinit var userPreference: UserPreference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
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

        userPreference = UserPreference(this)


        val faded = AnimationUtils.loadAnimation(this, R.anim.fades_anim)
        val boing = AnimationUtils.loadAnimation(this, R.anim.boing)
        val imageView = findViewById<ImageView>(R.id.imageView)
        val text1 = findViewById<TextView>(R.id.tv_appname)
        imageView.startAnimation(boing)
        binding.bgImageView.startAnimation(boing)
        text1.startAnimation(faded)

//        val decorView = window.decorView;
//        // ViewGroup you want to start blur from. Choose root as close to BlurView in hierarchy as possible.
//        val rootView = binding.root;
//
//        // Optional:
//        // Set drawable to draw in the beginning of each blurred frame.
//        // Can be used in case your layout has a lot of transparent space and your content
//        // gets a too low alpha value after blur is applied.
//        val windowBackground = decorView.background;
//
//        binding.blurView.setupWith(rootView, RenderScriptBlur(applicationContext)) // or RenderEffectBlur
//            .setFrameClearDrawable(windowBackground) // Optional
//            .setBlurRadius(16F).setBlurAutoUpdate(true)

        val r = Runnable {
            if (userPreference.getAuth() != null) {
                startActivity(Intent(this, HomesActivity::class.java ))

            } else { // Jika belum login, pergi ke halaman login
                startActivity(Intent(this, LoginActivity::class.java))
            }
            finish()
        }
        Handler().postDelayed(r, 2050)
    }

}