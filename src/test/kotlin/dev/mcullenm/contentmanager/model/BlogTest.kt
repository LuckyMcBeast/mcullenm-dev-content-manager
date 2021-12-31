package dev.mcullenm.contentmanager.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class BlogTest{

    @Test
    fun `should construct Blog object`(){
        val content : List<Content> = listOf(Content(0, "p", "content goes here"))
        val blog = Blog(blogId = 1, title = "My First Blog", publishDate = "12-09-2021", content = content)

        assertThat(blog.blogId).isEqualTo(1)
        assertThat(blog.title).isEqualTo("My First Blog")
        assertThat(blog.publishDate).isEqualTo("12-09-2021")
        assertThat(blog.content).isEqualTo(listOf(Content(0, "p", "content goes here")))
    }
}