package com.example.myspotify.repository

import android.util.Log
import com.example.myspotify.Constants
import com.example.myspotify.api.ApiInterface
import com.example.myspotify.models.Data
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class MusicListRepository  @Inject constructor(private val musicApi: ApiInterface) {

    private val _musicList = MutableStateFlow<List<Data>>(emptyList())
    val musicList : StateFlow<List<Data>>
        get() = _musicList


    suspend fun getMusicList(name: String){

        val categoriesList = mutableListOf<Data>()

        val response = musicApi.getData(name)

        if (response.isSuccessful && response.body() != null) {
            response.body()?.data?.let { dataList ->
                categoriesList.addAll(dataList)
            }
        } else {
            Log.d("API_ERROR", response.body().toString())
        }

        // Emit the final accumulated list
        _musicList.emit(categoriesList)

    }

}