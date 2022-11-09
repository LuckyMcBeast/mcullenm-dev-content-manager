package dev.mcullenm.contentmanager.repository.entity

import dev.mcullenm.contentmanager.model.Blog
import dev.mcullenm.contentmanager.model.Content
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class BlogEntityTest {

    private val publishDate: LocalDate =
        LocalDate.of(2021, 12, 9)

    @Test
    fun `should create BlogEntity from Blog without id`() {
        val content: List<Content> = listOf(Content(0, "p", "content goes here"))
        val blog = Blog(blogId = null, title = "My First Blog", publishDate = publishDate, content = content)
        val expected = BlogEntity(null, "My First Blog", publishDate = publishDate)

        val actual = BlogEntity.from(blog)

        assertThat(actual).isEqualToComparingFieldByField(expected)
    }
}
