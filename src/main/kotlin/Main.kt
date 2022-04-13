enum class PostType {
    AUDIO, VIDEO, PHOTO, DIGEST
}

data class Post(
    val id: Int,
    val type: PostType,
    val content: String,
    val likes: Int = 0
)

open class Node(var id: Int)

open class CrudService<T : Node> {
    private var nextId = 1
    val elements = mutableListOf<T>()

    fun add(elem: T): T {
        elem.id = nextId++
        elements.add(elem)
        return elements.last()
    }

    fun update(elem: T): Boolean {
        for ((index, node) in elements.withIndex()) {
            if (node.id == elem.id) {
                elements[index] = elem
                return true
            }

        }
        return false
    }

    fun getById(id: Int): T? = elements.find { it.id == id }
}

data class Comment(val text: String, var deleted: Boolean = false)
class Note(id: Int, private val text: String, val comments: MutableList<Comment> = mutableListOf()) : Node(id) {
    override fun toString(): String {
        return "Note id=$id with text=$text and comments: $comments"
    }
}

class NoteService() : CrudService<Note>() {
    fun addComment(noteId: Int, comment: Comment) {
        getById(noteId)?.comments?.add(comment)
    }
}

fun main() {
    val service = NoteService()
    println(service.elements)
    service.add(Note(0, "first"))
    service.add(Note(0, "second"))
    println(service.elements)
    service.update(Note(1, "first-updated"))
    service.update(Note(2, "second-updated"))
    service.update(Note(3, "third-updated"))
    println(service.getById(1))
    println(service.getById(2))
    println(service.getById(3))
    service.addComment(1, Comment("first comment"))
    println(service.getById(1))
}