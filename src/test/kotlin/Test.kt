import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class WallServiceTest {
    @BeforeEach
    fun clearBeforeTest() {
        WallService.clear()
    }

    @Test
    fun addShouldSetNonZeroId() {
        val post = Post(
            ownerId = 1,
            fromId = 1,
            date = System.currentTimeMillis(),
            text = "Тестовый пост"
        )

        val result = WallService.add(post)
        assertNotEquals(0, result.id)
    }

    @Test
    fun updateExistingPostReturnsTrue() {
        val post1 = WallService.add(
            Post(
                ownerId = 1,
                fromId = 1,
                date = System.currentTimeMillis(),
                text = "Пост 1"
            )
        )

        val updatedPost = post1.copy(text = "Обновленный текст")
        assertTrue(WallService.update(updatedPost))
    }

    @Test
    fun updateNonExistingPostReturnsFalse() {
        WallService.add(
            Post(
                ownerId = 1,
                fromId = 1,
                date = System.currentTimeMillis(),
                text = "Пост 1"
            )
        )

        val nonExistingPost = Post(
            id = 999,
            ownerId = 1,
            fromId = 1,
            date = System.currentTimeMillis(),
            text = "Несуществующий пост"
        )

        assertFalse(WallService.update(nonExistingPost))
    }
}