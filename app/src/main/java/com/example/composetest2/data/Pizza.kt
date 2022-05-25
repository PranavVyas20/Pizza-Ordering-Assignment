package com.example.composetest2.data

data class Pizza(
    val crusts: List<Crust>,
    val defaultCrust: Int,
    val description: String,
    val id: String,
    val isVeg: Boolean,
    val name: String
)