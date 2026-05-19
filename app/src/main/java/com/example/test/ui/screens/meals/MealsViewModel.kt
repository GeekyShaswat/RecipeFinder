package com.example.test.ui.screens.meals

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.data.remote.model.MealSummary
import com.example.test.data.repository.MealRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealsViewModel @Inject constructor(
    private val repository: MealRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val categoryName: String = savedStateHandle["categoryName"] ?: ""

    private val _uiState = MutableStateFlow<MealsUiState>(MealsUiState.Loading)
    val uiState: StateFlow<MealsUiState> = _uiState

    init {
        loadMeals()
    }

    private fun loadMeals() {
        viewModelScope.launch {
            _uiState.value = MealsUiState.Loading
            try {
                val response = repository.getMealsByCategory(categoryName)
                _uiState.value = MealsUiState.Success(response.meals ?: emptyList())
            } catch (e: Exception) {
                _uiState.value = MealsUiState.Error(e.message ?: "Something went wrong")
            }
        }
    }
}

sealed class MealsUiState {
    data object Loading : MealsUiState()
    data class Success(val meals: List<MealSummary>) : MealsUiState()
    data class Error(val message: String) : MealsUiState()
}