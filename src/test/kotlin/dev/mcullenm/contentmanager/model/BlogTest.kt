package dev.mcullenm.contentmanager.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class BlogTest {

    private val publishDate: LocalDate =
        LocalDate.of(2021, 12, 9)

    @Test
    fun `should construct Blog object`() {
        val content: List<Content> = listOf(Content(0, "p", "content goes here"))
        val blog = Blog(blogId = 1, title = "My First Blog", publishDate = publishDate, content = content)

        assertThat(blog.blogId).isEqualTo(1)
        assertThat(blog.title).isEqualTo("My First Blog")
        assertThat(blog.publishDate).isEqualTo(publishDate)
        assertThat(blog.content).isEqualTo(listOf(Content(0, "p", "content goes here")))
    }

    @Test
    fun `should provide publishDate if publishDate is null`() {
        val content: List<Content> = listOf(Content(0, "p", "content goes here"))
        val blog = Blog(blogId = 1, title = "My First Blog", publishDate = null, content = content)

        blog.providePublishDate()

        assertThat(blog.publishDate).isInstanceOf(LocalDate::class.java)
    }

    @Test
    fun `should not provide publishDate if publishDate is not null`() {
        val content: List<Content> = listOf(Content(0, "p", "content goes here"))
        val blog = Blog(blogId = 1, title = "My First Blog", publishDate = publishDate, content = content)

        blog.providePublishDate()

        assertThat(blog.publishDate).isEqualTo(publishDate)
    }
}
