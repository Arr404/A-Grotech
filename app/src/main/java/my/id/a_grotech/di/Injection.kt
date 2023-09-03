package my.id.a_grotech.di

import android.content.Context
import my.id.a_grotech.data.QnasRepository
import my.id.a_grotech.database.QnasDatabase
import my.id.a_grotech.network.ApiConfig

object Injection {
    fun provideRepository(context: Context): QnasRepository {
        val database = QnasDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        return QnasRepository(database, apiService)
    }
}