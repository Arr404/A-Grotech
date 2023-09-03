package my.id.a_grotech.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import my.id.a_grotech.network.QnasResponseItems
import java.util.*

class DataQnasTypeConverters {
    companion object {
        @TypeConverter
        fun stringToDataQnasList(data: String?): List<QnasResponseItems> {
            if (data == null) {
                return Collections.emptyList()
            }
            val listType = object : TypeToken<List<QnasResponseItems?>?>() {}.type
            return Gson().fromJson<List<QnasResponseItems>>(data, listType)
        }

        @TypeConverter
        fun DataQnasListToString(someObjects: List<QnasResponseItems?>?): String {
            return Gson().toJson(someObjects)
        }
    }
}