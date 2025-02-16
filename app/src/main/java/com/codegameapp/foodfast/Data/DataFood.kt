package com.codegameapp.foodfast.Data

import kotlinx.serialization.Serializable

@Serializable
data class Food(
    val name:String,
    val descriptor: String,
    val imageUrl:String,
    val price:Double,
    val rate:Double
)
