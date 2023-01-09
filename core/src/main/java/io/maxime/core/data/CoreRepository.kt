package io.maxime.core.data

import io.maxime.core.data.local.CoreLocalRepository
import io.maxime.core.data.local.model.JoinStory
import io.maxime.core.data.remote.CoreRemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.singleOrNull
import javax.inject.Inject

class CoreRepository @Inject constructor(
    val remote: CoreRemoteRepository,
    val local: CoreLocalRepository
) {

    val stories: Flow<List<JoinStory>> = local.stories

    suspend fun fetchData() {
        remote
            .getStories()
            .singleOrNull()
            ?.map {
                val story = JoinStory(
                    id = it.id.orEmpty(),
                    title = it.title.orEmpty(),
                    url = it.url.orEmpty(),
                    owner = it.owner.orEmpty(),
                    lastEditDate = it.lastEditDate ?: 0,
                    previewUrl = it.cover?.urls?.square.orEmpty()
                )

                local.saveStory(story)
            }
    }
}
