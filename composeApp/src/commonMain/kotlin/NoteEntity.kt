import kotlin.Long
import kotlin.String

public data class NoteEntity(
  public val id: Long,
  public val title: String,
  public val content: String,
  public val colorHex: Long,
  public val created: Long
) {
  public override fun toString(): String = """
  |NoteEntity [
  |  id: $id
  |  title: $title
  |  content: $content
  |  colorHex: $colorHex
  |  created: $created
  |]
  """.trimMargin()
}
