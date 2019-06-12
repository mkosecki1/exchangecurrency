package com.example.exchangecurrency.repository

import com.example.exchangecurrency.extention.safeApi
import com.example.exchangecurrency.network.NBPApiService

const val JSON_FORMAT = "json"

class Repository(private val nbpApiService: NBPApiService) {
    suspend fun getAllCurrencies() = safeApi { nbpApiService.getAllCurrencies(JSON_FORMAT).await() }
    suspend fun getGoldPrice() = safeApi { nbpApiService.getGoldPrice(JSON_FORMAT).await() }
    suspend fun getGoldPriceByDate(firstDate: String, lastDate: String) =
        safeApi { nbpApiService.getGoldPriceByDate(firstDate, lastDate, JSON_FORMAT).await() }
}