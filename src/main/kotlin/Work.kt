data class Post(
    val id: Int = 0,
    val ownerId: Int,
    val fromId: Int,
    val createdBy: Int? = null,
    val date: Long,
    val text: String,
    val replyOwnerId: Int? = null,
    val replyPostId: Int? = null,
    val friendsOnly: Boolean = false,
    val comments: Comments? = null,
    val likes: Likes? = null,
    val reposts: Reposts? = null,
    val views: Views? = null,
    val postType: String? = "post",
    val isFavorite: Boolean? = false,
    val attachments: Array<Attachment>? = null
)

data class Comments(
    val count: Int = 0,
    val canPost: Boolean = true,
    val groupsCanPost: Boolean = true,
    val canClose: Boolean = false,
    val canOpen: Boolean = false
)

data class Likes(
    val count: Int = 0,
    val userLikes: Boolean = false,
    val canLike: Boolean = true,
    val canPublish: Boolean = true
)

data class Reposts(
    val count: Int = 0,
    val userReposted: Boolean = false
)

data class Views(
    val count: Int
)

sealed class Attachment {
    abstract val type: String

    data class Photo(
        val id: Int,
        val ownerId: Int,
        val photo130: String,
        val photo604: String
    ) : Attachment() {
        override val type = "photo"
    }

    data class Video(
        val id: Int,
        val ownerId: Int,
        val title: String,
        val duration: Int
    ) : Attachment() {
        override val type = "video"
    }

    data class Audio(
        val id: Int,
        val ownerId: Int,
        val artist: String,
        val title: String,
        val duration: Int
    ) : Attachment() {
        override val type = "audio"
    }

    data class Document(
        val id: Int,
        val ownerId: Int,
        val title: String,
        val size: Int,
        val ext: String
    ) : Attachment() {
        override val type = "doc"
    }

    data class Link(
        val url: String,
        val title: String,
        val description: String
    ) : Attachment() {
        override val type = "link"
    }

    data class Poll(
        val id: Int,
        val ownerId: Int,
        val question: String,
        val votes: Int
    ) : Attachment() {
        override val type = "poll"
    }
}

object WallService {
    private var posts = emptyArray<Post>()
    private var nextId = 1

    fun add(post: Post): Post {
        val newPost = post.copy(id = nextId++)
        posts += newPost
        return newPost
    }

    fun update(post: Post): Boolean {
        for ((index, existingPost) in posts.withIndex()) {
            if (existingPost.id == post.id) {
                posts[index] = post
                return true
            }
        }
        return false
    }

    fun clear() {
        posts = emptyArray()
        nextId = 1
    }

    fun getLastPost(): Post? = posts.lastOrNull()
}

fun main() {
    WallService.clear()

    val photoAttachment = Attachment.Photo(
        id = 1,
        ownerId = 1,
        photo130 = "https://example.com/photo130.jpg",
        photo604 = "https://example.com/photo604.jpg"
    )

    val post = Post(
        ownerId = 1,
        fromId = 1,
        date = System.currentTimeMillis(),
        text = "Мой первый пост с фото!",
        attachments = arrayOf(photoAttachment)
    )

    val addedPost = WallService.add(post)
    println("Добавлен пост с ID: ${addedPost.id}")
    println("Тип первого вложения: ${addedPost.attachments?.get(0)?.type}")
    val updatedPost = addedPost.copy(text = "Обновлённый текст поста")
    val updateResult = WallService.update(updatedPost)
    println("Результат обновления: $updateResult")

    processAttachments(addedPost)
}

fun processAttachments(post: Post) {
    println("\nОбработка вложений для поста ${post.id}:")
    post.attachments?.forEach { attachment ->
        when (attachment) {
            is Attachment.Photo -> {
                println("Фото (${attachment.photo604})")
            }
            is Attachment.Video -> {
                println("Видео: ${attachment.title} (${attachment.duration} сек)")
            }
            is Attachment.Audio -> {
                println("Аудио: ${attachment.artist} - ${attachment.title}")
            }
            is Attachment.Document -> {
                println("Документ: ${attachment.title}.${attachment.ext} (${attachment.size} байт)")
            }
            is Attachment.Link -> {
                println("Ссылка: ${attachment.title} (${attachment.url})")
            }
            is Attachment.Poll -> {
                println("Опрос: ${attachment.question} (${attachment.votes} голосов)")
            }
        }
    } ?: println("Вложения отсутствуют")
}