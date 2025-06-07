import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class WallServiceTest {
    @BeforeEach
    fun clearBeforeTest() {
        WallService.clear()
    }

    @Test
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

    @Test
    fun testUpdateExistingPostReturnsTrue() {
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
        val update = post1.copy(text = "Текст")
        val result = WallService.update(update)
        assertTrue(result)
    }

    @Test
    fun testUpdateNonExistingPostReturnsFalse() {
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

        val nonExistingPost = Post(
            id = 100,
            ownerId = 100,
            fromId = 100,
            date = System.currentTimeMillis(),
            text = "Пост"
        )

        val result = WallService.update(nonExistingPost)

        assertFalse(result)
    }
}