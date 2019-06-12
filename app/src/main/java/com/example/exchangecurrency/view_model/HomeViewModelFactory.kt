package com.example.exchangecurrency.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.exchangecurrency.repository.Repository

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(private val repository: Repository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = HomeViewModel(repository) as T
}