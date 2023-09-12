package com.example.mealzapp.model.api

import com.example.mealzapp.model.MealEntity
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


class MealsApiService {

     private  var mealsApi : MealsApi? = null

    init{
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        mealsApi = retrofit.create(MealsApi::class.java)
    }

    suspend fun getMeals(): MealEntity{

        return mealsApi?.getMeals()!!
    }
}