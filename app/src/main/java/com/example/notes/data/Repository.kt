package com.example.notes.data

import com.example.notes.data.model.Note

object Repository{
    val notes: List<Note> = listOf(
        Note("Моя первая заметка",
            "Kotlin очень краткий, но при этом выразительный язык"),
        Note("Моя вторая заметка",
            "Kotlin легко изучать"),
        Note("Моя третья заметка",
            "Выучить английский"),
        Note("Моя четвертая заметка",
            "Купить кукурузные палочки"),
        Note("Моя пятая заметка",
            "Покорми кота"),
        Note("Моя шестая заметка",
            "Ты молодец, съешь мороженое"),
        Note("Моя седьмая заметка",
            "В 23:00 пора спать")
    )

//    fun getNotes() : List<Note>{
//        return notes
//    }
}