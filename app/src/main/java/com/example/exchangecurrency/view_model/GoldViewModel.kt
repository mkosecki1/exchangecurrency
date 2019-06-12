package com.example.exchangecurrency.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.exchangecurrency.models.Gold
import com.example.exchangecurrency.network.Result
import com.example.exchangecurrency.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class GoldViewModel(private val repository: Repository) : ViewModel() {
    private val job = Job()
    private val coroutineContext: CoroutineContext get() = job + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)
    val getGoldPriceSuccess = MutableLiveData<List<Gold>>()
    val getGoldPriceError = MutableLiveData<String>()
    val getGoldPriceException = MutableLiveData<Exception>()
    val getGoldPriceByDateSuccess = MutableLiveData<List<Gold>>()
    val getGoldPriceByDateError = MutableLiveData<String>()
    val getGoldPriceByDateException = MutableLiveData<Exception>()

    fun fetchGoldPrice() {
        scope.launch {
            when (val goldRespond = repository.getGoldPrice()) {
                is Result.Success -> getGoldPriceSuccess.postValue(goldRespond.data)
                is Result.Error -> getGoldPriceError.postValue(goldRespond.error)
                is Result.Exception -> getGoldPriceException.postValue(goldRespond.exception)
            }
        }
    }

    fun fetchGoldPriceByDate(firstDate: String, lastDate: String) {
        scope.launch {
            when (val goldRespondByDate = repository.getGoldPriceByDate(firstDate, lastDate)) {
                is Result.Success -> getGoldPriceByDateSuccess.postValue(goldRespondByDate.data)
                is Result.Error -> getGoldPriceByDateError.postValue(goldRespondByDate.error)
                is Result.Exception -> getGoldPriceByDateException.postValue(goldRespondByDate.exception)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}