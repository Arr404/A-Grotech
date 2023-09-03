package my.id.a_grotech

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.material.snackbar.Snackbar
import my.id.a_grotech.`object`.LoginRequest
import my.id.a_grotech.`object`.RegisterRequest
import my.id.a_grotech.`object`.UserAuth
import my.id.a_grotech.`object`.UserPreference
import my.id.a_grotech.network.LoginResponse
import my.id.a_grotech.databinding.ActivityLoginBinding
import my.id.a_grotech.network.ApiConfig
import my.id.a_grotech.network.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding
    private lateinit var userPreference: UserPreference
    private var daftar=true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPreference = UserPreference(this)

        val blurView = binding.rootFkBlurView
        blurView.setBlur(this, blurView)

        binding.buttonSignUp.setOnClickListener {
            if(daftar){
                binding.buttonSignin.text=getString(R.string.sign_up);
                binding.buttonSignUp.text=getString(R.string.sign_in);
                binding.editTextName.visibility = View.VISIBLE
                binding.editTextConfirmPassword.visibility = View.VISIBLE
                daftar = !daftar
            }else{
                binding.buttonSignin.text=getString(R.string.sign_in);
                binding.buttonSignUp.text=getString(R.string.sign_up);
                binding.editTextName.visibility = View.GONE
                binding.editTextConfirmPassword.visibility = View.GONE
                daftar = !daftar
            }
        }
        binding.buttonSignin.setOnClickListener {
            if(daftar){
                login()
            }else{
                register()
            }

        }
    }
    private fun showError(message: String){
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun login(){
        val email = binding.editTextEmail.text.toString()
        val password = binding.editTextPassword.text.toString()
        if (password != "") {
            val client = ApiConfig.getApiService().login(LoginRequest(email,password))
            client.enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            val loginResult = responseBody.data
                            if (loginResult != null) {
                                userPreference.setAuth(UserAuth(email, loginResult.token))
                                startActivity(Intent(this@LoginActivity, HomesActivity::class.java))
                                finish()
                            }else{
                                showError(responseBody.message)
                            }
                        }
                    } else {
                        val responseBody = response.body()
                        if (responseBody != null) showError(responseBody.message) else showError(getString(R.string.unknown_error))
                        Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                        Log.e(ContentValues.TAG, "data: a")
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    showError(getString(R.string.unknown_error))
                    Log.e(ContentValues.TAG, "onFailure: ${t.message}")
                    Log.e(ContentValues.TAG, "data: b $call")
                }
            })
        }
    }
    private fun register(){
        val name = binding.editTextName.text.toString()
        val userName = name+"-agrotech"
        val email = binding.editTextEmail.text.toString()
        val password = binding.editTextPassword.text.toString()
        val confirmPassword = binding.editTextConfirmPassword.text.toString()
        if (password != "") {
            val client = ApiConfig.getApiService().register(RegisterRequest(name,userName,email,password, confirmPassword))
            client.enqueue(object : Callback<RegisterResponse> {
                override fun onResponse(
                    call: Call<RegisterResponse>,
                    response: Response<RegisterResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            if (responseBody.isSuccess) {
                                showError(responseBody.message)
                                binding.buttonSignin.text="Masuk"
                                binding.editTextName.visibility = View.GONE
                                binding.editTextConfirmPassword.visibility = View.GONE
                                daftar = !daftar
                            }else{
                                showError(responseBody.message)
                            }
                        }
                    } else {
                        showError(getString(R.string.unknown_error))
                        Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                        Log.e(ContentValues.TAG, "data: a")
                    }
                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    showError(getString(R.string.unknown_error))
                    Log.e(ContentValues.TAG, "onFailure: ${t.message}")
                    Log.e(ContentValues.TAG, "data: b $call")
                }
            })
        }
    }
}