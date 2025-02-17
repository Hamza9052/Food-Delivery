package com.codegameapp.foodfast.MVVM

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.codegameapp.foodfast.API.ApiFood
import com.codegameapp.foodfast.Data.DataFood
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import javax.inject.Inject

@HiltViewModel
class FoodMVVM @Inject constructor(application: Application)  : ViewModel() {


    private val _listFood = MutableLiveData<List<DataFood>>(mutableListOf<DataFood>())
    val listFood: LiveData<List<DataFood>> get() = _listFood.map { it.toList() }

     suspend fun FetchData_food() {
        val api = ApiFood.create()
        Log.e("foodtest", "$api")
        try {
            Log.e("foodtest", "$api")
            val response = api.getFoods()
            Log.e("foodtest", "$response")
            _listFood.value = response
        } catch (e: Exception) {
            Log.e("foodtest", "$e")
        }

    }

}
class ViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(FoodMVVM::class.java)) {
            FoodMVVM(application) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}