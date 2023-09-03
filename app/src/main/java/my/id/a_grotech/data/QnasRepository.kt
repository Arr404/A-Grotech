package my.id.a_grotech.data

import androidx.lifecycle.LiveData
import androidx.paging.*
import my.id.a_grotech.database.QnasDatabase
import my.id.a_grotech.network.ApiService
import my.id.a_grotech.network.QnasResponseItem

class QnasRepository(private val qnasDatabase: QnasDatabase, private val apiService: ApiService) {
    fun getQnas(token:String): LiveData<PagingData<QnasResponseItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = QnasRemoteMediator(token,qnasDatabase, apiService),
            pagingSourceFactory = {
//                QnasPagingSource(apiService)
                qnasDatabase.qnasDao().getAllQnas()
            }
        ).liveData
    }
}