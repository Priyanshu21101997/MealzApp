package com.example.mealzapp.model.api

import com.example.mealzapp.model.MealEntity
import retrofit2.http.GET

interface MealsApi{
    @GET("categories.php")
    suspend fun getMeals(): MealEntity
}