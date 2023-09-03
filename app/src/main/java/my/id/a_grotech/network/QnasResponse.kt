package my.id.a_grotech.network

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "qnas")
data class QnasResponse(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @field:SerializedName("isSuccess")
    val isSuccess: Boolean = false,

    @field:SerializedName("statusCode")
    val statusCode: Int?,

    @field:SerializedName("timestamp")
    val timestamp: Int?,

    @field:SerializedName("data")
    val data: List<QnasResponseItem>,

)

data class QnasResponseItem(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("tittle")
    val tittle: String? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("likes")
    val likes: String? = null,

    @field:SerializedName("createdBy")
    @Embedded
    val createdBy: createdBy?,

    @field:SerializedName("isSolved")
    val isSolved: Boolean = false
)

data class createdBy(

    @field:SerializedName("id")
    val idCreatedBy: Int?,

    @field:SerializedName("name")
    val name: String?,

    @field:SerializedName("picture")
    val picture: String?
)

