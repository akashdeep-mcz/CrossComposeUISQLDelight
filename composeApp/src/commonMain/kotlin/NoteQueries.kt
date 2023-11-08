import com.squareup.sqldelight.Query
import com.squareup.sqldelight.Transacter
import kotlin.Any
import kotlin.Long
import kotlin.String
import kotlin.Unit

public interface NoteQueries : Transacter {
  public fun <T : Any> getAllNotes(mapper: (
    id: Long,
    title: String,
    content: String,
    colorHex: Long,
    created: Long
  ) -> T): Query<T>

  public fun getAllNotes(): Query<NoteEntity>

  public fun <T : Any> getNoteById(id: Long, mapper: (
    id: Long,
    title: String,
    content: String,
    colorHex: Long,
    created: Long
  ) -> T): Query<T>

  public fun getNoteById(id: Long): Query<NoteEntity>

  public fun insertNote(
    id: Long?,
    title: String,
    content: String,
    colorHex: Long,
    created: Long
  ): Unit

  public fun deleteNoteById(id: Long): Unit
}
