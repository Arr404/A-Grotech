package com.agilfuad.fireforest.ui.dashboard

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.agilfuad.fireforest.inprofileActivity
import com.google.firebase.database.*

class DashboardViewModel : ViewModel() {
//    private val _text = MutableLiveData<String>().apply {
//        value = "ini text"
//
//    }
    private var database: DatabaseReference? = null
    private val daftarprofil:MutableList<sens> = ArrayList()

    private val _text = MutableLiveData<String>().apply {

        database = FirebaseDatabase.getInstance().reference
        database?.child("test")?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                /**
                 * Saat ada data baru, masukkan datanya ke ArrayList
                 */
                daftarprofil
                for (noteDataSnapshot in dataSnapshot.children) {
                    /**
                     * Mapping data pada DataSnapshot ke dalam object Barang
                     * Dan juga menyimpan primary key pada object Barang
                     * untuk keperluan Edit dan Delete data
                     */
                    val user: sens? = noteDataSnapshot.getValue(sens::class.java)
                    /**
                     * Menambahkan object Barang yang sudah dimapping
                     * ke dalam ArrayList
                     */

                    if (user != null) {
                        daftarprofil.add(user)
                        value = "Sensor yang terbaca adalah : "+user.data.toString()+" C"
                        Log.d("sensor", user.data.toString())
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
//        for (user in daftarprofil) {
//            value = user.data.toString()
//        }
    }
    val text: LiveData<String> = _text
}