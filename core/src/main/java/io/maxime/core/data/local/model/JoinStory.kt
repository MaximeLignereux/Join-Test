package io.maxime.core.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "join_story")
data class JoinStory(
    @PrimaryKey @ColumnInfo(name = "join_story_id") val id: String,
    val title: String,
    val owner: String,
    val lastEditDate: Long,
    val url: String,
    val previewUrl: String
)
