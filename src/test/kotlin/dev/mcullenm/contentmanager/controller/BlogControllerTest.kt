package dev.mcullenm.contentmanager.controller

import com.nhaarman.mockitokotlin2.whenever
import dev.mcullenm.contentmanager.model.Blog
import dev.mcullenm.contentmanager.model.Content
import dev.mcullenm.contentmanager.service.BlogService
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations

internal class BlogControllerTest {

    @InjectMocks
    lateinit var blogController: BlogController

    @Mock
    lateinit var blogService: BlogService

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `should get blogs from from blogService`() {
        val blogs = listOf<Blog>()
        whenever(blogService.getBlogs()).thenReturn(blogs)

        val actual = blogController.getBlogs()

        assertThat(actual).isEqualTo(blogs)
    }

    @Test
    fun `should get a blog from blogService by id`() {
        val blog = Blog(1, "Test", "date", listOf<Content>())
        whenever(blogService.getBlog(1)).thenReturn(blog)

        val actual = blogController.getBlog(1)

        assertThat(actual).isEqualTo(blog).usingRecursiveComparison()
    }
}
