package my.id.a_grotech

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.SyncStateContract
import androidx.appcompat.app.AppCompatActivity

import my.id.a_grotech.databinding.ActivityResultBinding
import java.io.FileInputStream


class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private var config: HashMap<String, String> = HashMap()
    private var imgpath: String? = null
    private var bmp: Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        config.put("cloud_name", "myCloudName")
        config.put("api_key", "")
        config.put("api_secret", "-keutM")
//        MediaManager.init(this, config);


        val filename = intent.getStringExtra("image")
        try {
            val `is`: FileInputStream = openFileInput(filename)
            bmp = BitmapFactory.decodeStream(`is`)
            binding.previewImageView.setImageBitmap(bmp)
//            val cloudinary = Cloudinary(SyncStateContract.Constants.CLOUDINARY_URL)
//            try {
//                val `is`: FileInputStream = FileInputStream(File(filePath))
//                val uploader: Uploader = cloudinary.uploader()
//                return uploader.upload(`is`, HashMap())
//            } catch (e: FileNotFoundException) {
//                e.printStackTrace()
//            } catch (e: IOException) {
//                e.printStackTrace()
//            }
            `is`.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


}