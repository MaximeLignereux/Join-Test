package io.maxime.join.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import io.maxime.join.presentation.ui.theme.JoinStoriesTheme
import io.maxime.stories.JoinApp
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var uiState: MainActivityUiState by mutableStateOf(MainActivityUiState.Init)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState
                    .onEach {
                        uiState = it
                    }
                    .collect()
            }
        }

        setContent {
            JoinStoriesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFEBF2F2)
                ) {
                    when (uiState) {
                        MainActivityUiState.Init -> Unit
                        is MainActivityUiState.Success -> JoinApp()
                    }
                }
            }
        }
    }
}
