package com.example.exchangecurrency

import android.app.Application
import com.example.exchangecurrency.network.NBPApiService
import com.example.exchangecurrency.repository.Repository
import com.example.exchangecurrency.view_model.GoldViewModelFactory
import com.example.exchangecurrency.view_model.HomeViewModelFactory
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApplication : Application(), KodeinAware {

    companion object {
        private const val BASE_URL = "http://api.nbp.pl/api/"
    }

    override val kodein by Kodein.lazy {
        bind() from singleton {
            Interceptor { chain ->
                val newUrl =
                    chain
                        .request()
                        .url()
                        .newBuilder()
                        .build()
                val newRequest =
                    chain
                        .request()
                        .newBuilder()
                        .url(newUrl)
                        .build()
                chain.proceed(newRequest)
            }
        }
        bind() from singleton {
            OkHttpClient()
                .newBuilder()
                .addInterceptor(instance())
                .build()
        }

        bind() from singleton { GsonConverterFactory.create() }
        bind() from singleton { CoroutineCallAdapterFactory() }

        bind() from singleton {
            Retrofit.Builder()
                .client(instance())
                .baseUrl(BASE_URL)
                .addConverterFactory(instance<GsonConverterFactory>())
                .addCallAdapterFactory(instance<CoroutineCallAdapterFactory>())
                .build()
        }

        bind() from singleton { instance<Retrofit>().create(NBPApiService::class.java) }
        bind() from singleton { Repository(instance()) }
        bind() from singleton { HomeViewModelFactory(instance()) }
        bind() from singleton { GoldViewModelFactory(instance()) }

    }


}