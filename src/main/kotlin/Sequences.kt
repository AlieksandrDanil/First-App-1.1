class Message(private val text: String) {
    override fun toString(): String {
        return "\nMsg text=$text"
    }
}

class Chat(val messages: MutableList<Message> = mutableListOf()) {
    companion object {
        private var counter: Int = 0
        fun counter(): Int {
            return counter
        }
    }
    init {
        counter++
    }
    val chatId = counter()
    override fun toString(): String {
        return "\nChat id=${this.chatId} with Messages=$messages"
    }
}

class ChatService {
    private val chats = hashMapOf<List<String>, Chat>()

    fun getChats(userId: String): List<Chat> {
        return chats.filter { entry -> entry.key.contains(userId) }.values.toList()
    }

    fun getChat(userId: String, chatId: Int): Chat? {
        return getChats(userId).find { it.chatId == chatId }
    }

    fun addMessage(userIds: List<String>, message: Message): Chat {
        //var id: Int = 1
        //chats.filter { entry -> entry.key.contains(userId) }.values.toList()
//        if (chats[userIds] != null) {
//            chats[userIds]?.messages?.add(message)
//        } else {
//            chats[userIds] = Chat(1, mutableListOf(message))
//        }
//        return chats[userIds] ?: Chat(-1)
        return chats.getOrPut(userIds) {
            Chat()
        }.apply {
            messages.add(message)
        }
    }
}

fun main() {
    val service = ChatService()
    service.addMessage(listOf("Tanya", "Sasha"), Message("My First Chat & first msg"))
    service.addMessage(listOf("Sasha", "Ivan"), Message("My Second Chat & first msg"))
    service.addMessage(listOf("Tanya", "Sasha"), Message("My First Chat & second msg"))
    service.addMessage(listOf("Sasha", "Petya"), Message("My repeat userIds"))
    println(service.getChats("Ivan"))
    println(service.getChat("Petya", 3))
}
