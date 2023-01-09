package io.maxime.core.data.local

import io.maxime.core.data.local.dao.CoreDao
import io.maxime.core.data.local.model.JoinStory
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CoreLocalRepository @Inject constructor(private val dao: CoreDao) {

    val stories: Flow<List<JoinStory>> = dao.getStories()

    fun saveStory(story: JoinStory) {
        dao.save(story)
    }

}
