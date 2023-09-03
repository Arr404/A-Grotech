package my.id.a_grotech.network

import my.id.a_grotech.`object`.LoginRequest
import my.id.a_grotech.`object`.RegisterRequest
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @POST("api/auth/Login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("api/auth/Register")
    fun register(@Body RegisterRequest: RegisterRequest): Call<RegisterResponse>

    @GET("api/Discussion")
    suspend fun getQnas(
        @Header("Authorization") bearerAndToken: String,
        @Query("PageNumber") page: Int,
        @Query("PageSize") size: Int
    ): List<QnasResponseItem>
}