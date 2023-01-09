package io.maxime.stories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.maxime.core.data.CoreRepository
import io.maxime.core.data.local.model.JoinStory
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class StoryViewModel @Inject constructor(coreRepository: CoreRepository) : ViewModel() {

    val uiState: StateFlow<StoryUiState> =
        coreRepository.stories
            .map {
                StoryUiState.Data(it.first())
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = StoryUiState.Loading
            )

    val uiStoriesState: StateFlow<StoriesUiState> =
        coreRepository.stories
            .map {
                StoriesUiState.Data(it)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = StoriesUiState.Loading
            )
}

sealed interface StoryUiState {
    object Loading : StoryUiState
    data class Data(val story: JoinStory) : StoryUiState
}

sealed interface StoriesUiState {
    object Loading : StoriesUiState
    data class Data(val stories: List<JoinStory>) : StoriesUiState
}
