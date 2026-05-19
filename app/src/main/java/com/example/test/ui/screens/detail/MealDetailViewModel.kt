package com.example.test.ui.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.data.local.entity.FavoriteMealEntity
import com.example.test.data.remote.model.MealDetail
import com.example.test.data.repository.MealRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealDetailViewModel @Inject constructor(
    private val repository: MealRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val mealId: String = savedStateHandle["mealId"] ?: ""

    private val _uiState = MutableStateFlow<MealDetailUiState>(MealDetailUiState.Loading)
    val uiState: StateFlow<MealDetailUiState> = _uiState

    val isFavorite: StateFlow<Boolean> = repository.isFavorite(mealId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), false)

    init {
        loadMealDetail()
    }

    private fun loadMealDetail() {
        viewModelScope.launch {
            _uiState.value = MealDetailUiState.Loading
            try {
                val meal = repository.getMealDetail(mealId).meals?.firstOrNull()
                _uiState.value = if (meal != null) {
                    MealDetailUiState.Success(meal)
                } else {
                    MealDetailUiState.Error("Meal not found")
                }
            } catch (e: Exception) {
                _uiState.value = MealDetailUiState.Error(e.toString())
            }
        }
    }

    fun toggleFavorite() {
        val state = _uiState.value as? MealDetailUiState.Success ?: return
        val meal = state.meal
        viewModelScope.launch {
            val entity = FavoriteMealEntity(
                idMeal = meal.idMeal,
                strMeal = meal.strMeal,
                strMealThumb = meal.strMealThumb.orEmpty(),
                strCategory = meal.strCategory
            )
            if (isFavorite.value) repository.removeFavorite(entity)
            else repository.addFavorite(entity)
        }
    }
}

sealed class MealDetailUiState {
    data object Loading : MealDetailUiState()
    data class Success(val meal: MealDetail) : MealDetailUiState()
    data class Error(val message: String) : MealDetailUiState()
}