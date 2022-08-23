package dev.mcullenm.contentmanager.model

import dev.mcullenm.contentmanager.repository.entity.ContentEntity
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test

internal class ContentTest {

    @Test
    fun `should create from ContentEntity`() {
        val contentEntity = ContentEntity(blogId = 1, position = 0, "p", "This is some content.")
        val expected = Content(0, "p", "This is some content.")

        val actual = Content.fromEntity(contentEntity)

        assertThat(actual).isEqualTo(expected)
    }
}
