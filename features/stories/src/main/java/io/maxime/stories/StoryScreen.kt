package io.maxime.stories

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

enum class StoryScreen {
    Start,
    StoryDetail
}

@Composable
fun JoinApp(
    navController: NavHostController = rememberNavController()
) {

    NavHost(
        navController = navController,
        startDestination = StoryScreen.Start.name
    ) {
        composable(route = StoryScreen.Start.name) {
            StoryPreview(
                onPreviewClickEvent = {
                    navController.navigate(StoryScreen.StoryDetail.name)
                }
            )
        }
        composable(route = StoryScreen.StoryDetail.name) {
            WebStories()
        }
    }

}

