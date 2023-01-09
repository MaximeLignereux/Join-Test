package io.maxime.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.maxime.core.data.local.model.JoinStory
import kotlinx.coroutines.flow.Flow

@Dao
interface CoreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(story: JoinStory)

    @Query("SELECT * FROM join_story ORDER BY lastEditDate")
    fun getStories(): Flow<List<JoinStory>>

    @Query("SELECT * FROM join_story WHERE join_story_id IN (:id)")
    fun getStory(id: String): JoinStory
}
