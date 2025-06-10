import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class WallServiceTest {
    @BeforeEach
    fun clearBeforeTest() {
        WallService.clear()
    }

    @Test
    fun `add should set non-zero id`() {
        val post = Post(
            ownerId = 1,
            fromId = 1,
            date = System.currentTimeMillis(),
            text = "Test post"
        )
        val result = WallService.add(post)
        assertNotEquals(0, result.id)
    }

    @Test
    fun `update existing post returns true`() {
        val post = WallService.add(
            Post(
                ownerId = 1,
                fromId = 1,
                date = System.currentTimeMillis(),
                text = "Original post"
            )
        )
        val updatedPost = post.copy(text = "Updated text")
        assertTrue(WallService.update(updatedPost))
    }

    @Test
    fun `post with photo attachment should store correctly`() {
        val photoAttachment = Attachment.PhotoAttachment(
            Photo(
                id = 1,
                ownerId = 1,
                photo130 = "small.jpg",
                photo604 = "large.jpg"
            )
        )

        val post = Post(
            ownerId = 1,
            fromId = 1,
            date = System.currentTimeMillis(),
            text = "Post with photo",
            attachments = arrayOf(photoAttachment)
        )

        val addedPost = WallService.add(post)

        assertNotNull(addedPost.attachments)
        assertEquals(1, addedPost.attachments?.size)
        assertEquals("photo", addedPost.attachments?.get(0)?.type)

        val storedPhoto = addedPost.attachments?.get(0) as? Attachment.PhotoAttachment
        assertNotNull(storedPhoto)
        assertEquals("large.jpg", storedPhoto?.photo?.photo604)
    }

    @Test
    fun `when using sealed class no else branch needed in when`() {
        val photoAttachment = Attachment.PhotoAttachment(Photo(1, 1, "small.jpg", "large.jpg"))
        val videoAttachment = Attachment.VideoAttachment(Video(1, 1, "Cool video", 120))

        val result1 = processAttachment(photoAttachment)
        val result2 = processAttachment(videoAttachment)

        assertEquals("Photo: large.jpg", result1)
        assertEquals("Video: Cool video (120 sec)", result2)
    }

    private fun processAttachment(attachment: Attachment): Any {
        return when (attachment) {
            is Attachment.PhotoAttachment -> "Photo: ${attachment.photo.photo604}"
            is Attachment.VideoAttachment -> "Video: ${attachment.video.title} (${attachment.video.duration} sec)"
            is Attachment.AudioAttachment -> "Audio: ${attachment.audio.artist} - ${attachment.audio.title}"
            is Attachment.DocumentAttachment -> "Document: ${attachment.doc.title}.${attachment.doc.ext}"
            is Attachment.LinkAttachment -> "Link: ${attachment.link.title}"
            else -> {}
        }
    }
}