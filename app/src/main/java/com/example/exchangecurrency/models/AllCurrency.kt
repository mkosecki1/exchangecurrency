package com.example.exchangecurrency.models

data class AllCurrency(
    val effectiveDate: String,
    val no: String,
    val rates: List<Rate>,
    val table: String
) {
    data class Rate(
        val code: String,
        val currency: String,
        val mid: Double
    )
}