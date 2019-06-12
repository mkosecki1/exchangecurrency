package com.example.exchangecurrency.ui.view.adapters

import android.widget.TextView
import androidx.databinding.BindingAdapter

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("bindDoubleToString")
    fun bindDoubleToString(textView: TextView, double: Double) {
        textView.text = "$double zł"
    }
}