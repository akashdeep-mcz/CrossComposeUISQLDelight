import com.squareup.sqldelight.Transacter
import com.squareup.sqldelight.db.SqlDriver

public interface NoteDatabase : Transacter {
  public val noteQueries: NoteQueries

  public companion object {
    public val Schema: SqlDriver.Schema
      get() = NoteDatabase::class.schema

    public operator fun invoke(driver: SqlDriver): NoteDatabase =
        NoteDatabase::class.newInstance(driver)
  }
}
