package com.romakumari.recyclerandsplash

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Room
import androidx.room.RoomDatabase

//@Database(version=1,entities=[])

@Database(version = 1, entities = [NotesData::class])
abstract class NotesDb : RoomDatabase() {
    abstract fun notesdao(): NotesDao


    companion object {
        var notesdb: NotesDb? = null
        fun getDatabase(context: Context): NotesDb {
            if (notesdb == null)
                notesdb = Room.databaseBuilder(
                    context,
                    NotesDb::class.java,
                    context.resources.getString(R.string.app_name)
                ).build()
            return notesdb!!
        }
    }
}