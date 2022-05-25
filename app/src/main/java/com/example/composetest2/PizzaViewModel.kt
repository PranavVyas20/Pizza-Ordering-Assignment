package com.example.composetest2

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetest2.data.Crust
import com.example.composetest2.data.Pizza
import com.example.composetest2.data.Size
import com.example.pizzaordering_assignment.RetrofitApi.RetrofitInstance
import kotlinx.coroutines.*

class PizzaViewModel:ViewModel() {
    var pizzaCrustList:List<Crust> by mutableStateOf(listOf())
    var crustSizeList:List<Size> by mutableStateOf(listOf())

    fun getPizzaCrusts(){
        viewModelScope.launch {
            try {
                val mPizza = RetrofitInstance.api.getPizza()
                pizzaCrustList = mPizza.crusts
                crustSizeList = pizzaCrustList[0].sizes
                Log.d("tagggg",pizzaCrustList.toString())
            }catch (e:Exception){
                Log.d("taggg","error fecthing data")
            }
        }
    }
}