package com.example.mealzapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealzapp.model.Categories
import com.example.mealzapp.model.MealEntity
import com.example.mealzapp.model.Repository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


class MAViewModel(private val repository: Repository = Repository()): ViewModel() {

    val ld : MutableLiveData<ArrayList<Categories>> = MutableLiveData()

//    suspend fun getMealData(): ArrayList<Categories> {
//        return repository.getMealsFromApi().categories
//    }

    init{
        viewModelScope.launch {
            ld.postValue(repository.getMealsFromApi().categories)
        }
    }


}