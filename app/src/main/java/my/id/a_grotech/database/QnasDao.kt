package my.id.a_grotech.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import my.id.a_grotech.network.QnasResponse
import my.id.a_grotech.network.QnasResponseItem

@Dao
interface QnasDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQnas(qnas: List<QnasResponseItem>)

    @Query("SELECT * FROM qnas")
    fun getAllQnas(): PagingSource<Int, QnasResponseItem>

    @Query("DELETE FROM qnas")
    suspend fun deleteAll()
}