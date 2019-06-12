package com.example.exchangecurrency.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.exchangecurrency.models.AllCurrency
import com.example.exchangecurrency.network.Result
import com.example.exchangecurrency.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class HomeViewModel(private val repository: Repository) : ViewModel() {
    private val job = Job()
    private val coroutineContext: CoroutineContext get() = job + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)
    val getAllCurrencySuccess = MutableLiveData<List<AllCurrency>>()
    val getAllCurrencyError = MutableLiveData<String>()
    val getAllCurrencyException = MutableLiveData<Exception>()

    init {
        fetchRateList()
    }

    private fun fetchRateList() {
        scope.launch {
            when (val rateRespond = repository.getAllCurrencies()) {
                is Result.Success -> getAllCurrencySuccess.postValue(rateRespond.data as List<AllCurrency>)
                is Result.Error -> getAllCurrencyError.postValue(rateRespond.error)
                is Result.Exception -> getAllCurrencyException.postValue(rateRespond.exception)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}