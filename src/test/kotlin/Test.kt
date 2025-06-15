import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

class WallServiceTest {
    private val service = WallService()

    @Before
    fun setUp() {
        service.clear()
    }

    @Test
    fun createComment_shouldAddCommentToExistingPost() {
        val post = Post(
            id = 1,
            ownerId = 1,
            fromId = 1,
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

        val comment = Comment(
            id = 0,
            fromId = 1,
            date = 1,
            text = "Тест комента",
            replyToUser = 0,
            replyToComment = 0,
            attachments = emptyArray(),
            parentsStack = emptyArray(),
            thread = Thread(0, emptyArray(), true, true, false)
        )

        val result = service.createComment(post.id, comment)
        assertNotEquals(0, result.id)
        assertEquals(comment.text, result.text)
    }

    @Test(expected = PostNotFoundException::class)
    fun createComment_shouldThrowWhenPostNotFound() {
        val comment = Comment(
            id = 0,
            fromId = 1,
            date = 1,
            text = "Тест комента",
            replyToUser = 0,
            replyToComment = 0,
            attachments = emptyArray(),
            parentsStack = emptyArray(),
            thread = Thread(0, emptyArray(), true, true, false)
        )

        service.createComment(999, comment)
    }
}