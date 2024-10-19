package com.example.myspotify.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myspotify.models.Data
import com.example.myspotify.repository.MusicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(private val repository: MusicRepository): ViewModel() {

    val musicCategory : StateFlow<List<Data>>
        get() = repository.musicCategory


    init {
        viewModelScope.launch {
            repository.getCategories()
        }
    }

}