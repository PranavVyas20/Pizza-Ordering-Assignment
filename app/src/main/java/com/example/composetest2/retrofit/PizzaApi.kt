package com.example.pizzaordering_assignment.RetrofitApi

import com.example.composetest2.data.Pizza
import retrofit2.Response
import retrofit2.http.GET

interface PizzaApi {

    @GET("/api/v1/pizza/1")
    suspend fun getPizza(): Pizza

}