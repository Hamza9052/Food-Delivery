package com.codegameapp.foodfast.Data

import com.google.gson.annotations.SerializedName

data class MealResponse(
    @SerializedName("meals") val meals: List<Meal>
)

data class Meal(
    @SerializedName("idMeal") val id: String,
    @SerializedName("strMeal") val name: String,
    @SerializedName("strMealThumb") val image: String,
    @SerializedName("strInstructions") val description: String
)
