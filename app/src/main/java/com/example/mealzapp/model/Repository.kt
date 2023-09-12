package com.example.mealzapp.model

import com.example.mealzapp.model.api.MealsApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository(private val mealsApiService: MealsApiService = MealsApiService()) {

    suspend fun getMealsFromApi(): MealEntity {
       return mealsApiService.getMeals()
    }
}