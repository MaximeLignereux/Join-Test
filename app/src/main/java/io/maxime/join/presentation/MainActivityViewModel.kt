package io.maxime.join.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.maxime.core.data.CoreRepository
import io.maxime.core.data.local.model.JoinStory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val repository: CoreRepository
) : ViewModel() {

    val uiState: StateFlow<MainActivityUiState> = repository.stories.map {
        if(it.isNotEmpty()) MainActivityUiState.Success(it) else MainActivityUiState.Init
    }.stateIn(
        scope = viewModelScope,
        initialValue = MainActivityUiState.Init,
        started = SharingStarted.WhileSubscribed(5_000)
    )

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.fetchData()
        }
    }

}

sealed interface MainActivityUiState {
    object Init : MainActivityUiState
    data class Success(val stories: List<JoinStory>) : MainActivityUiState
}
