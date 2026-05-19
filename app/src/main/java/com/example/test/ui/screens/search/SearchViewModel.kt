package com.example.test.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.data.remote.model.MealDetail
import com.example.test.data.repository.MealRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: MealRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Idle)
    val uiState: StateFlow<SearchUiState> = _uiState

    fun search(query: String) {
        if (query.isBlank()) return
        viewModelScope.launch {
            _uiState.value = SearchUiState.Loading
            try {
                val meals = repository.searchMeals(query).meals
                _uiState.value = if (meals.isNullOrEmpty()) SearchUiState.Empty
                else SearchUiState.Success(meals)
            } catch (e: Exception) {
                _uiState.value = SearchUiState.Error(e.message ?: "Something went wrong")
            }
        }
    }
}

sealed class SearchUiState {
    data object Idle : SearchUiState()
    data object Loading : SearchUiState()
    data object Empty : SearchUiState()
    data class Success(val meals: List<MealDetail>) : SearchUiState()
    data class Error(val message: String) : SearchUiState()
}