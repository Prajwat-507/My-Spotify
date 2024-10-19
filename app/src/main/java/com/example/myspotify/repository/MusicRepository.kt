package com.example.myspotify.repository

import android.util.Log
import com.example.myspotify.Constants
import com.example.myspotify.api.ApiInterface
import com.example.myspotify.models.Data
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


class MusicRepository @Inject constructor(private val musicApi: ApiInterface) {

    private val _musicCategory = MutableStateFlow<List<Data>>(emptyList())
    val musicCategory : StateFlow<List<Data>>
        get() = _musicCategory


    suspend fun getCategories(){

        val categoriesList = mutableListOf<Data>()

        for (name in Constants.MusicArtistName) {
            val response = musicApi.getData(name)

            if (response.isSuccessful && response.body() != null) {
                response.body()?.data?.get(0)?.let { dataList ->
                    categoriesList.addAll(listOf(dataList))
                }
            } else {
                Log.d("API_ERROR", response.body().toString())
            }
        }

        // Emit the final accumulated list
        _musicCategory.emit(categoriesList)

    }
}