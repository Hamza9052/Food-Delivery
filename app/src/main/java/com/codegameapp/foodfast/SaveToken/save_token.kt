package com.codegameapp.foodfast.SaveToken

import android.content.Context

class save_token(private val context: Context) {

    companion object{
        private const val MY_PREF_KEY ="PREF_KEY"
    }

    fun saveStringData(key:String,data:String?){
        val sharedPreferences = context.getSharedPreferences(MY_PREF_KEY, Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(key,data).apply()
    }
    fun getStringData(key:String):String?{
        val sharedPreferences = context.getSharedPreferences(MY_PREF_KEY, Context.MODE_PRIVATE)
        return sharedPreferences.getString(key,null)
    }
}