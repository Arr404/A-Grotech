package com.agilfuad.fireforest.ui.login

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.agilfuad.fireforest.DashboardActivity
import com.agilfuad.fireforest.databinding.ActivityLoginBinding

import com.agilfuad.fireforest.R
import com.agilfuad.fireforest.inprofileActivity
import com.google.firebase.database.*
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding
    private var activeButton = true
    private var database: DatabaseReference? = null
    private val daftarprofil:MutableList<profiles> = ArrayList()
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        editor = sharedPreferences.edit()

        if(sharedPreferences.getString("isLogin", "false").equals("true")){
            startActivity(Intent(this, inprofileActivity::class.java))
            finish()
        }

        val back = binding.backLogin
        back?.setOnClickListener {
            startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
            finish()
        }


        database = FirebaseDatabase.getInstance().reference
        database?.child("users")?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                /**
                 * Saat ada data baru, masukkan datanya ke ArrayList
                 */
                //daftarprofil
                for (noteDataSnapshot in dataSnapshot.children) {
                    /**
                     * Mapping data pada DataSnapshot ke dalam object Barang
                     * Dan juga menyimpan primary key pada object Barang
                     * untuk keperluan Edit dan Delete data
                     */
                    val user: profiles? = noteDataSnapshot.getValue(profiles::class.java)
                    /**
                     * Menambahkan object Barang yang sudah dimapping
                     * ke dalam ArrayList
                     */

                    if (user != null) {
                        daftarprofil.add(user)
                        Log.d("nama", user.nama)
                        Log.d("pass", user.password)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                /**
                 * Kode ini akan dipanggil ketika ada error dan
                 * pengambilan data gagal dan memprint error nya
                 * ke LogCat
                 */
                println(databaseError.details + " " + databaseError.message)
            }
        })


        val username = binding.username
        val password = binding.password
        val login = binding.login
        val loading = binding.loading

        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
            }
            setResult(Activity.RESULT_OK)

            //Complete and destroy login activity once successful
            finish()
        })

        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            username.text.toString(),
                            password.text.toString()
                        )
                }
                false
            }

            login.setOnClickListener {
                loading.visibility = View.VISIBLE
                loginViewModel.login(username.text.toString(), password.text.toString())
            }
        }
        button_handler(username.text.toString(), password.text.toString())
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName
        // TODO : initiate successful logged in experience
        Toast.makeText(
            applicationContext,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
    private fun button_handler(username: String, password: String){
        val loginButton = binding.login
        val daftarButton = binding.register

        Log.d("button", activeButton.toString())
        val nama = binding.name
        loginButton.setOnClickListener {
            Log.d("button", "onClick")
            if(activeButton){
                val inputGmailTmp = binding.username.text.toString()
                val inputPassTmp = md5(binding.password.text.toString())
                // Toast.makeText(this, inputPassTmp, Toast.LENGTH_SHORT).show()
                for (user in daftarprofil){
                    if((user.gmail).equals(inputGmailTmp) ){
                        if((user.password).equals(inputPassTmp)){
                            editor.putString("isLogin", "true")
                            editor.putString("nama", user.nama)
                            editor.putString("email", user.gmail)
                            editor.putString("password",  user.password)
                            editor.putInt("premium",  user.premium)
                            editor.putString("avatar", user.avatar_url)
                            editor.apply()
                            Toast.makeText(this, "benar", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, inprofileActivity::class.java))
                            finish()
                        }else{
                            basicAlert("Salah", "Maaf password atau gmail salah")
                            Log.d("password input", inputPassTmp)
                            Log.d("password", user.password)
                        }
                    }
                }

            }else{
                daftarButton?.setBackgroundColor(ContextCompat.getColor(this, R.color.white_Bone))
                loginButton.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
                nama?.setVisibility(View.INVISIBLE)
                activeButton = true
            }
        }
        daftarButton?.setOnClickListener {
            Log.d("button", "onClick")
            if(activeButton){
                daftarButton.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
                loginButton.setBackgroundColor(ContextCompat.getColor(this, R.color.white_Bone))
                nama?.setVisibility(View.VISIBLE)
                activeButton = false
            }else{
                val avatarUrlDefault = "https://drive.google.com/uc?id=1iY1XbKwpBn6qZK6f78ZJje94Bi64ck7P"
                val namaInputData = binding.name?.text.toString()
                val gmailInputData = binding.username.text.toString()
                val passInputData = binding.password.text.toString()

                savedata(database!!,avatarUrlDefault, namaInputData, gmailInputData, passInputData)
            }
        }
    }
    fun savedata(database: DatabaseReference,avatar: String, nama: String, gmail: String, pass: String) {
        val dataTmp = profiles()
        dataTmp.avatar_url = avatar
        dataTmp.nama = nama
        dataTmp.gmail = gmail
        dataTmp.password = md5(pass)
        dataTmp.premium = 0

        val key = database.push().key.toString()
        database.child("users").child(key).setValue(dataTmp).addOnCompleteListener {
            Toast.makeText(this, "Successs", Toast.LENGTH_SHORT).show()
        }
    }
    fun basicAlert(title: String, Massage: String){
        val builder = AlertDialog.Builder(this)

        with(builder)
        {
            setTitle(title)
            setMessage(Massage)
            setPositiveButton("oke", null)
            show()
        }
    }
    fun md5(s: String): String {
        try {
            // Create MD5 Hash
            val digest: MessageDigest = MessageDigest.getInstance("MD5")
            digest.update(s.toByteArray())
            val messageDigest: ByteArray = digest.digest()

            // Create Hex String
            val hexString = StringBuffer()
            for (i in messageDigest.indices) hexString.append(
                Integer.toHexString(
                    0xFF and messageDigest[i]
                        .toInt()
                )
            )
            return hexString.toString()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return ""
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}
class profiles (){
    var avatar_url: String = ""
    var nama: String = ""
    var gmail: String = ""
    var password: String = ""
    var premium: Int = 0
}
