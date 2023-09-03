package my.id.a_grotech.`object`

import android.content.Context
import android.content.SharedPreferences

class UserPreference(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("UserPreference", 0)

//    Dipanggil ketika berhasil login
    fun setAuth(userAuth: UserAuth) {
        sharedPreferences.edit()
            .putString("session", userAuth.session)
            .putString("token", userAuth.token)
            .commit()
    }

//    Untuk mengecek apakah sudah login atau belum, null berarti belum login
    fun getAuth(): UserAuth? {
        val session = sharedPreferences.getString("session", null)
        val token = sharedPreferences.getString("token", null)
        if (session != null && token != null) {
            return UserAuth(session, token)
        }
        return null
    }
//  Dipanggil ketika logout
    fun resetAuth() {
        sharedPreferences.edit().clear().commit()
    }
}