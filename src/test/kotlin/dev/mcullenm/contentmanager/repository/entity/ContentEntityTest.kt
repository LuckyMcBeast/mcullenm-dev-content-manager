package dev.mcullenm.contentmanager.repository.entity

import dev.mcullenm.contentmanager.model.Content
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test

internal class ContentEntityTest {

    @Test
    fun `should create list of Content from list of ContentEntity`() {
        val contentEntities = listOf(
            ContentEntity(
                blogId = 0,
                position = 0,
                type = "p",
                value = "This is some content."
            ),
            ContentEntity(
                blogId = 0,
                position = 1,
                type = "p",
                value = "This is some content."
            ),
        )
        val expected = listOf(
            Content(
                position = 0,
                type = "p",
                value = "This is some content."
            ),
            Content(
                position = 1,
                type = "p",
                value = "This is some content."
            )
        )

        assertThat(contentEntities.toContentList()).isEqualTo(expected)
    }
}
