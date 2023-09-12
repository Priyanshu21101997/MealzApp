package com.example.mealzapp.model

import com.google.gson.annotations.SerializedName

data class MealEntity (
    @SerializedName("categories" ) var categories : ArrayList<Categories> = arrayListOf()
)

data class Categories (
    @SerializedName("idCategory"             ) var idCategory             : String? = "0",
    @SerializedName("strCategory"            ) var strCategory            : String? = "ok",
    @SerializedName("strCategoryThumb"       ) var strCategoryThumb       : String? = "ok",
    @SerializedName("strCategoryDescription" ) var strCategoryDescription : String? = "OK"
)