package com.example.test.ui.screens.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.data.remote.model.Category
import com.example.test.data.repository.MealRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val repository: MealRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<CategoryUiState>(CategoryUiState.Loading)
    val uiState: StateFlow<CategoryUiState> = _uiState

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            _uiState.value = CategoryUiState.Loading
            try {
                val response = repository.getCategories()
                _uiState.value = CategoryUiState.Success(response.categories)
            } catch (e: Exception) {
                _uiState.value = CategoryUiState.Error(e.toString())
            }
        }
    }
}

sealed class CategoryUiState {
    data object Loading : CategoryUiState()
    data class Success(val categories: List<Category>) : CategoryUiState()
    data class Error(val message: String) : CategoryUiState()
}