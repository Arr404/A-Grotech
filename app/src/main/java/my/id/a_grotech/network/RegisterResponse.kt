package my.id.a_grotech.network

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

    @field:SerializedName("data")
    val data: RegisterResult?,

    @field:SerializedName("isSuccess")
    val isSuccess: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("statusCode")
    val statusCode: Int,

    @field:SerializedName("timestamp")
    val timestamp: Int,
)

data class RegisterResult(

    @field:SerializedName("token")
    val token: String?,

    @field:SerializedName("expired")
    val expired: String?
)