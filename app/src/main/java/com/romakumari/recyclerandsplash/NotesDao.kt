package com.romakumari.recyclerandsplash

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NotesDao {
    @Insert
    fun  insertNotes(notesData: NotesData)

    @Query("Select* from NotesData")
    fun getNotes() : List<NotesData>
    @Delete
    fun deleteNotes(notesData: NotesData)
    @Update
    fun updateNotes(notesData: NotesData)
}