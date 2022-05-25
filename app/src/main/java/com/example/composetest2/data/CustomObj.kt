package com.example.composetest2.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class CustomObj(val orderedPizzas:List<CustomPizza>):Parcelable