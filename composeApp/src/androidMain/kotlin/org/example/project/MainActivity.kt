package org.example.project

import App
import DateTimeUtil
import Note
import Note.Companion.generateRandomColor
import NoteDataSource
import NoteDatabase
import SqlDelightNoteDataSource
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.squareup.sqldelight.db.SqlDriver
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var sqlDriver: SqlDriver
    private lateinit var noteDataSource: NoteDataSource
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sqlDriver = DatabaseDriverFactory(applicationContext).createDriver()
        noteDataSource = SqlDelightNoteDataSource(NoteDatabase(sqlDriver))
        setContent {
            App(action = {
                val newNote = Note(
                    id = System.currentTimeMillis(),
                    title = it,
                    content = it,
                    colorHex = generateRandomColor(),
                    created = DateTimeUtil.now()
                )
                doThis(newNote)

            })
        }
    }


    private fun doThis(note: Note) {
        lifecycleScope.launch {
            noteDataSource.insertNote(note)
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}