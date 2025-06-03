//Quest 1
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
    val comments: Comments = Comments(),
    val likes: Likes = Likes(),
    val reposts: Reposts = Reposts(),
    val views: Views? = null,
    val postType: String = "Пост",
    val isFavorite: Boolean = false
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

//Quest 2
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
}

//Quest 3
class WallServiceTest {
    private fun clearBeforeTest() {
        WallService.clear()
    }

    private fun assertTrue(condition: Boolean) {
        if (!condition) throw AssertionError("Ложь")
    }

    private fun assertFalse(condition: Boolean) {
        if (condition) throw AssertionError("Правда")
    }

    private fun assertNotEquals(unexpected: Int, actual: Int) {
        if (unexpected == actual) throw AssertionError("Не равно: $unexpected")
    }

    fun runAllTests() {
        try {
            clearBeforeTest()
            testAddShouldSetNonZeroId()

            clearBeforeTest()
            testUpdateExistingPostReturnsTrue()

            clearBeforeTest()
            testUpdateNonExistingPostReturnsFalse()

            println("Тест пройден")
        } catch (e: AssertionError) {
            println("Ошибка: ${e.message}")
        }
    }

    fun testAddShouldSetNonZeroId() {
        val post = Post(
            ownerId = 1,
            fromId = 1,
            date = System.currentTimeMillis(),
            text = "Тестовый Пост"
        )

        val result = WallService.add(post)
        assertNotEquals(0, result.id)
    }

    fun testUpdateExistingPostReturnsTrue() {
        // Add test posts
        val post1 = WallService.add(
            Post(
                ownerId = 1,
                fromId = 1,
                date = System.currentTimeMillis(),
                text = "Пост 1"
            )
        )
        val post2 = WallService.add(
            Post(
                ownerId = 2,
                fromId = 2,
                date = System.currentTimeMillis(),
                text = "Пост 2"
            )
        )

        // Create update for post1
        val update = post1.copy(text = "Текст")

        // Perform update
        val result = WallService.update(update)

        // Verify
        assertTrue(result)
    }

    fun testUpdateNonExistingPostReturnsFalse() {
        // Add test posts
        WallService.add(
            Post(
                ownerId = 1,
                fromId = 1,
                date = System.currentTimeMillis(),
                text = "Пост 1"
            )
        )
        WallService.add(
            Post(
                ownerId = 2,
                fromId = 2,
                date = System.currentTimeMillis(),
                text = "Пост 2"
            )
        )

        // Create post with non-existing ID
        val nonExistingPost = Post(
            id = 100,
            ownerId = 100,
            fromId = 100,
            date = System.currentTimeMillis(),
            text = "Пост"
        )

        // Perform update
        val result = WallService.update(nonExistingPost)

        // Verify
        assertFalse(result)
    }
}

fun main(args: Array<String>) {
    val tester = WallServiceTest()

    println("Запуск тестов...")
    tester.runAllTests()

    println("\nЗапуск отдельных тестов:")

    WallService.clear()
    println("Тест 1 - добавление поста:")
    tester.testAddShouldSetNonZeroId()

    WallService.clear()
    println("Тест 2 - обновление существующего поста:")
    tester.testUpdateExistingPostReturnsTrue()

    WallService.clear()
    println("Тест 3 - обновление несуществующего поста:")
    tester.testUpdateNonExistingPostReturnsFalse()

    println("\nВсе тесты завершены!")
}

