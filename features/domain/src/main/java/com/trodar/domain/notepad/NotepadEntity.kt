package com.trodar.domain.notepad

import com.trodar.room.model.Notepad
import java.util.Date

class NotepadEntity(
    override val id: Int = 0,
    override val txt: String = "",
    override val date: Date = Date()
) : Notepad {
   val title = if (txt.length > 30) txt.substring(0, 29).replace("\n", " ") else txt.replace("\n", " ")
}