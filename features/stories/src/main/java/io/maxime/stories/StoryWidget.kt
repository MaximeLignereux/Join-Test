package io.maxime.stories

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.google.accompanist.pager.*
import com.google.android.material.math.MathUtils.lerp
import io.maxime.core.data.local.model.JoinStory
import kotlin.math.absoluteValue

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun StoryPreview(
    viewModel: StoryViewModel = hiltViewModel(),
    onPreviewClickEvent: () -> Unit
) {
    val storyUiState by viewModel.uiState.collectAsStateWithLifecycle()
    when (storyUiState) {
        StoryUiState.Loading -> Unit
        is StoryUiState.Data -> {
            val story = (storyUiState as StoryUiState.Data).story
            StoryPreviewItem(story, onPreviewClickEvent)
        }
    }
}

@Composable
fun StoryPreviewItem(
    story: JoinStory,
    onPreviewClickEvent: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable {
            onPreviewClickEvent.invoke()
        }

    ) {
        AsyncImage(
            model = story.previewUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(10.dp)
                .size(128.dp)
                .clip(CircleShape)
        )
        Text(story.owner)
    }
}


@OptIn(
    ExperimentalLifecycleComposeApi::class,
    ExperimentalPagerApi::class
)
@Composable
fun WebStories(
    viewModel: StoryViewModel = hiltViewModel()
) {

    val storiesUiState by viewModel.uiStoriesState.collectAsStateWithLifecycle()
    when (storiesUiState) {
        StoriesUiState.Loading -> Unit
        is StoriesUiState.Data -> {
            val stories = (storiesUiState as StoriesUiState.Data).stories
            val pagerState = rememberPagerState()
            HorizontalPager(
                count = stories.size,
                state = pagerState,
                //contentPadding = PaddingValues(horizontal = 8.dp),
                modifier = Modifier
                    .fillMaxWidth()
            ) { page ->
                val story = stories[page]
                Card(
                    Modifier
                        .graphicsLayer {
                            // Calculate the absolute offset for the current page from the
                            // scroll position. We use the absolute value which allows us to mirror
                            // any effects for both directions
                            val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue

                            // We animate the scaleX + scaleY, between 85% and 100%
                            lerp(
                                0.85f,
                                1f,
                                1f - pageOffset.coerceIn(0f, 1f)
                            ).also { scale ->
                                scaleX = scale
                                scaleY = scale
                            }

                            // We animate the alpha, between 50% and 100%
                            alpha = lerp(
                                0.5f,
                                1f,
                                1f - pageOffset.coerceIn(0f, 1f)
                            )
                        }
                        .fillMaxHeight()
                ) {
                    WebStory(story)
                }
            }
        }
    }
}

@Composable
fun WebStory(story: JoinStory) {
    AndroidView(factory = {
        WebView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            loadUrl(story.url)
        }
    }, update = {
        it.loadUrl(story.url)
    })
}

