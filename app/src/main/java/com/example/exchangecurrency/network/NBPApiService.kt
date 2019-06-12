package com.example.exchangecurrency.network

import com.example.exchangecurrency.models.AllCurrency
import com.example.exchangecurrency.models.Gold
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NBPApiService {

    @GET("exchangerates/tables/a/")
    fun getAllCurrencies(@Query("format") format: String): Deferred<Response<List<AllCurrency>>>

    @GET("cenyzlota")
    fun getGoldPrice(@Query("format") format: String): Deferred<Response<List<Gold>>>

    @GET("cenyzlota/{firstDate}/{lastDate}/")
    fun getGoldPriceByDate(
        @Path("firstDate") firstDate: String, @Path("lastDate") lastDate: String, @Query(
            "format"
        ) format: String
    ): Deferred<Response<List<Gold>>>
}