package io.maxime.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import io.maxime.core.data.local.dao.CoreDao
import io.maxime.core.data.local.model.JoinStory

@Database(
    entities = [JoinStory::class],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun coreDao(): CoreDao
}
