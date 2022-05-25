package com.example.composetest2.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CustomPizza(val base:String,val size:String,val totalAmount:Int) : Parcelable