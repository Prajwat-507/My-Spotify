package com.example.myspotify.viewModel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myspotify.models.Data
import com.example.myspotify.repository.MusicListRepository
import com.example.myspotify.repository.MusicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel  @Inject constructor(private val repository: MusicListRepository, private val savedState: SavedStateHandle): ViewModel() {
    val musicList : StateFlow<List<Data>>
        get() = repository.musicList


    init {
        viewModelScope.launch {
            Log.d("SAVEDSTATEEEEE", savedState.keys().toString())
            repository.getMusicList(savedState.get<String>("arg").toString())
        }
    }
}