package dev.mcullenm.contentmanager.model

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.module.kotlin.readValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class BlogTest {

    @Test
    fun `should construct Blog object`() {
        val content: List<Content> = listOf(Content(0, "p", "content goes here"))
        val blog = Blog(blogId = 1, title = "My First Blog", publishDate = "12-09-2021", content = content)

        assertThat(blog.blogId).isEqualTo(1)
        assertThat(blog.title).isEqualTo("My First Blog")
        assertThat(blog.publishDate).isEqualTo("12-09-2021")
        assertThat(blog.content).isEqualTo(listOf(Content(0, "p", "content goes here")))
    }

    @Test
    fun `should create Blog object from ObjectNode`() {
        val objectNode = ObjectMapper().readValue(
            """{"blog_id":15,"title":"Test","publish_date":"2022-01-13 06:10:58.632369","content":[{"position":0,"type":"p","value":"This is some content"},{"position":1,"type":"p","value":"This is some content"}]}""",
            ObjectNode::class.java
        )
        val expected = Blog(15, "Test", "2022-01-13 06:10:58.632369", listOf(Content(0, "p", "This is some content"), Content(1, "p", "This is some content")))

        val actual = Blog.from(objectNode)

        assertThat(actual).isEqualTo(expected)
    }
}
