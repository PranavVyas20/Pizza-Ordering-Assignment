package com.example.pizzaordering_assignment.RetrofitApi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val api: PizzaApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://625bbd9d50128c570206e502.mockapi.io")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PizzaApi::class.java)
    }
}