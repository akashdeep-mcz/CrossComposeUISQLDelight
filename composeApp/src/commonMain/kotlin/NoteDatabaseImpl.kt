import com.squareup.sqldelight.Query
import com.squareup.sqldelight.TransacterImpl
import com.squareup.sqldelight.db.SqlCursor
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.internal.copyOnWriteList
import kotlin.reflect.KClass

internal val KClass<NoteDatabase>.schema: SqlDriver.Schema
  get() = NoteDatabaseImpl.Schema

internal fun KClass<NoteDatabase>.newInstance(driver: SqlDriver): NoteDatabase =
    NoteDatabaseImpl(driver)

private class NoteDatabaseImpl(
  driver: SqlDriver
) : TransacterImpl(driver), NoteDatabase {
  public override val noteQueries: NoteQueriesImpl = NoteQueriesImpl(this, driver)

  public object Schema : SqlDriver.Schema {
    public override val version: Int
      get() = 1

    public override fun create(driver: SqlDriver): Unit {
      driver.execute(null, """
          |CREATE TABLE noteEntity(
          |    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
          |    title TEXT NOT NULL,
          |    content TEXT NOT NULL,
          |    colorHex INTEGER NOT NULL,
          |    created INTEGER NOT NULL
          |)
          """.trimMargin(), 0)
    }

    public override fun migrate(
      driver: SqlDriver,
      oldVersion: Int,
      newVersion: Int
    ): Unit {
    }
  }
}

private class NoteQueriesImpl(
  private val database: NoteDatabaseImpl,
  private val driver: SqlDriver
) : TransacterImpl(driver), NoteQueries {
  internal val getAllNotes: MutableList<Query<*>> = copyOnWriteList()

  internal val getNoteById: MutableList<Query<*>> = copyOnWriteList()

  public override fun <T : Any> getAllNotes(mapper: (
    id: Long,
    title: String,
    content: String,
    colorHex: Long,
    created: Long
  ) -> T): Query<T> = Query(-623426313, getAllNotes, driver, "note.sq", "getAllNotes", """
  |SELECT *
  |FROM noteEntity
  """.trimMargin()) { cursor ->
    mapper(
      cursor.getLong(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getLong(3)!!,
      cursor.getLong(4)!!
    )
  }

  public override fun getAllNotes(): Query<NoteEntity> = getAllNotes { id, title, content, colorHex,
      created ->
    NoteEntity(
      id,
      title,
      content,
      colorHex,
      created
    )
  }

  public override fun <T : Any> getNoteById(id: Long, mapper: (
    id: Long,
    title: String,
    content: String,
    colorHex: Long,
    created: Long
  ) -> T): Query<T> = GetNoteByIdQuery(id) { cursor ->
    mapper(
      cursor.getLong(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getLong(3)!!,
      cursor.getLong(4)!!
    )
  }

  public override fun getNoteById(id: Long): Query<NoteEntity> = getNoteById(id) { id_, title,
      content, colorHex, created ->
    NoteEntity(
      id_,
      title,
      content,
      colorHex,
      created
    )
  }

  public override fun insertNote(
    id: Long?,
    title: String,
    content: String,
    colorHex: Long,
    created: Long
  ): Unit {
    driver.execute(-1395401654, """
    |INSERT OR REPLACE
    |INTO noteEntity(
    |    id,
    |    title,
    |    content,
    |    colorHex,
    |    created
    |) VALUES(?, ?, ?, ?, ?)
    """.trimMargin(), 5) {
      bindLong(1, id)
      bindString(2, title)
      bindString(3, content)
      bindLong(4, colorHex)
      bindLong(5, created)
    }
    notifyQueries(-1395401654) {
        database.noteQueries.getNoteById +
                database.noteQueries.getAllNotes
    }
  }

  public override fun deleteNoteById(id: Long): Unit {
    driver.execute(1652369454, """
    |DELETE FROM noteEntity
    |WHERE id = ?
    """.trimMargin(), 1) {
      bindLong(1, id)
    }
    notifyQueries(1652369454) { database.noteQueries.getNoteById + database.noteQueries.getAllNotes }
  }

  private inner class GetNoteByIdQuery<out T : Any>(
    public val id: Long,
    mapper: (SqlCursor) -> T
  ) : Query<T>(getNoteById, mapper) {
    public override fun execute(): SqlCursor = driver.executeQuery(-825247173, """
    |SELECT *
    |FROM noteEntity
    |WHERE id = ?
    """.trimMargin(), 1) {
      bindLong(1, id)
    }

    public override fun toString(): String = "note.sq:getNoteById"
  }
}
