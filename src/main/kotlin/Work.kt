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

    data class PhotoAttachment(
        val photo: Photo
    ) : Attachment() {
        override val type = "photo"
    }

    data class VideoAttachment(
        val video: Video
    ) : Attachment() {
        override val type = "video"
    }

    data class AudioAttachment(
        val audio: Audio
    ) : Attachment() {
        override val type = "audio"
    }

    data class DocumentAttachment(
        val doc: Document
    ) : Attachment() {
        override val type = "doc"
    }

    data class LinkAttachment(
        val link: Link
    ) : Attachment() {
        override val type = "link"
    }

    data class PollAttachment(
        val poll: Poll
    ) : Attachment() {
        override val type = "poll"
    }
}

data class Photo(
    val id: Int,
    val ownerId: Int,
    val photo130: String,
    val photo604: String
)

data class Video(
    val id: Int,
    val ownerId: Int,
    val title: String,
    val duration: Int
)

data class Audio(
    val id: Int,
    val ownerId: Int,
    val artist: String,
    val title: String,
    val duration: Int
)

data class Document(
    val id: Int,
    val ownerId: Int,
    val title: String,
    val size: Int,
    val ext: String
)

data class Link(
    val url: String,
    val title: String,
    val description: String
)

data class Poll(
    val id: Int,
    val ownerId: Int,
    val question: String,
    val votes: Int
)

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

    val photoAttachment = Attachment.PhotoAttachment(
        Photo(
            id = 1,
            ownerId = 1,
            photo130 = "https://example.com/photo130.jpg",
            photo604 = "https://example.com/photo604.jpg"
        )
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
            is Attachment.PhotoAttachment -> {
                println("Фото (${attachment.photo.photo604})")
            }
            is Attachment.VideoAttachment -> {
                println("Видео: ${attachment.video.title} (${attachment.video.duration} сек)")
            }
            is Attachment.AudioAttachment -> {
                println("Аудио: ${attachment.audio.artist} - ${attachment.audio.title}")
            }
            is Attachment.DocumentAttachment -> {
                println("Документ: ${attachment.doc.title}.${attachment.doc.ext} (${attachment.doc.size} байт)")
            }
            is Attachment.LinkAttachment -> {
                println("Ссылка: ${attachment.link.title} (${attachment.link.url})")
            }
            is Attachment.PollAttachment -> {
                println("Опрос: ${attachment.poll.question} (${attachment.poll.votes} голосов)")
            }
        }
    } ?: println("Вложения отсутствуют")
}