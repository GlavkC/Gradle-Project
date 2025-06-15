import java.sql.DriverManager.println

class PostNotFoundException(message: String) : RuntimeException(message)

data class Post(
    val id: Int,
    val ownerId: Int,
    val fromId: Int,
    val createdBy: Int,
    val date: Int,
    val text: String,
    val replyOwnerId: Int,
    val replyPostId: Int,
    val friendsOnly: Boolean,
    val comments: Comments,
    val copyright: Copyright,
    val likes: Likes,
    val reposts: Reposts,
    val views: Views,
    val postType: String,
    val signerId: Int,
    val canPin: Boolean,
    val canDelete: Boolean,
    val canEdit: Boolean,
    val isPinned: Boolean,
    val markedAsAds: Boolean,
    val isFavorite: Boolean,
    val postponedId: Int
)

data class Comment(
    val id: Int,
    val fromId: Int,
    val date: Int,
    val text: String,
    val replyToUser: Int,
    val replyToComment: Int,
    val attachments: Array<Attachment>,
    val parentsStack: Array<Int>,
    val thread: Thread
)

data class Attachment(
    val type: String,
    val url: String
)

data class Thread(
    val count: Int,
    val items: Array<Comment>,
    val canPost: Boolean,
    val showReplyButton: Boolean,
    val groupsCanPost: Boolean
)

data class Comments(
    val count: Int,
    val canPost: Boolean,
    val groupsCanPost: Boolean,
    val canClose: Boolean,
    val canOpen: Boolean
)

data class Copyright(
    val id: Int,
    val link: String,
    val name: String,
    val type: String
)

data class Likes(
    val count: Int,
    val userLikes: Boolean,
    val canLike: Boolean,
    val canPublish: Boolean
)

data class Reposts(
    val count: Int,
    val userReposted: Boolean
)

data class Views(
    val count: Int
)

class WallService {
    private var posts = emptyArray<Post>()
    private var comments = emptyArray<Comment>()
    private var nextCommentId = 1

    fun addPost(post: Post): Post {
        posts += post
        return posts.last()
    }

    fun createComment(postId: Int, comment: Comment): Comment {
        val postExists = posts.any { it.id == postId }
        if (!postExists) {
            throw PostNotFoundException("Пост id $postId ненайден")
        }

        val newComment = comment.copy(id = nextCommentId++)
        comments += newComment
        return newComment
    }

    fun clear() {
        posts = emptyArray()
        comments = emptyArray()
        nextCommentId = 1
    }
}
fun main() {
    val service = WallService()

    val post = Post(
        id = 1,
        ownerId = 1,
        fromId = 1,  // Добавлен недостающий параметр
        createdBy = 1,
        date = 1,
        text = "Тест поста",
        replyOwnerId = 0,
        replyPostId = 0,
        friendsOnly = false,
        comments = Comments(0, true, false, false, false),
        copyright = Copyright(0, "", "", ""),
        likes = Likes(0, false, true, true),
        reposts = Reposts(0, false),
        views = Views(0),
        postType = "пост",
        signerId = 0,
        canPin = true,
        canDelete = true,
        canEdit = true,
        isPinned = false,
        markedAsAds = false,
        isFavorite = false,
        postponedId = 0
    )

    service.addPost(post)
    println("Пост успешно добавлен")
}