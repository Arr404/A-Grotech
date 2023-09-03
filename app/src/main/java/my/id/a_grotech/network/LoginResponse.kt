package my.id.a_grotech.network

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @field:SerializedName("data")
    val data: LoginResult?,

    @field:SerializedName("isSuccess")
    val isSuccess: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("statusCode")
    val statusCode: Int,

    @field:SerializedName("timestamp")
    val timestamp: Int,
)

data class LoginResult(

    @field:SerializedName("token")
    val token: String,

    @field:SerializedName("expired")
    val expired: String
)