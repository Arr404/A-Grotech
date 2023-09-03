package my.id.a_grotech.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import my.id.a_grotech.network.QnasResponse
import my.id.a_grotech.network.QnasResponseItem

@Database(
    entities = [QnasResponseItem::class, RemoteKeys::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(DataQnasTypeConverters::class)
abstract class QnasDatabase : RoomDatabase() {

    abstract fun qnasDao(): QnasDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {
        @Volatile
        private var INSTANCE: QnasDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): QnasDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    QnasDatabase::class.java, "qnas_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}