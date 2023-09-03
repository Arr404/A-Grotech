package my.id.a_grotech.ui.qnas

import android.content.Context
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import my.id.a_grotech.data.QnasRepository
import my.id.a_grotech.di.Injection
import my.id.a_grotech.network.QnasResponseItem


class QnasViewModel constructor(private val qnasRepository: QnasRepository) : ViewModel() {

    fun qnas(token: String): LiveData<PagingData<QnasResponseItem>> =
        qnasRepository.getQnas(token).cachedIn(viewModelScope)
}

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QnasViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return QnasViewModel(Injection.provideRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}