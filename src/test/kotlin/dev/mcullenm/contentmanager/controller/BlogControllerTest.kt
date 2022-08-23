package dev.mcullenm.contentmanager.controller

import com.nhaarman.mockitokotlin2.whenever
import dev.mcullenm.contentmanager.model.Blog
import dev.mcullenm.contentmanager.model.Content
import dev.mcullenm.contentmanager.model.request.CreateBlogRequest
import dev.mcullenm.contentmanager.model.response.CreateBlogResponse
import dev.mcullenm.contentmanager.model.response.DeleteBlogResponse
import dev.mcullenm.contentmanager.service.BlogService
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.time.LocalDate

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
        val blog = Blog(1, "Test", LocalDate.now(), listOf<Content>())
        whenever(blogService.getBlog(1)).thenReturn(blog)

        val actual = blogController.getBlog(1)

        assertThat(actual).isEqualTo(blog).usingRecursiveComparison()
    }

    @Test
    fun `should create blog with blogService`() {
        val createBlogRequest = CreateBlogRequest("Test", listOf())
        val createBlogResponse = CreateBlogResponse(true, 1, 0)
        whenever(blogService.postBlog(createBlogRequest, null)).thenReturn(createBlogResponse)

        val actual = blogController.postBlog(createBlogRequest)

        assertThat(actual).isEqualTo(createBlogResponse)
    }

    @Test
    fun `should update blog with blogService`() {
        val createBlogRequest = CreateBlogRequest("Test", listOf())
        val createBlogResponse = CreateBlogResponse(true, 1, 0)
        whenever(blogService.updateBlog(1, createBlogRequest)).thenReturn(createBlogResponse)

        val actual = blogController.putBlog(1, createBlogRequest)

        assertThat(actual).isEqualTo(createBlogResponse)
    }

    @Test
    fun `should delete blog with blogService`() {
        val deleteBlogResponse = DeleteBlogResponse(true, 1)
        whenever(blogService.deleteBlog(1)).thenReturn(deleteBlogResponse)

        val actual = blogController.deleteBlog(1)

        assertThat(actual).isEqualTo(deleteBlogResponse)
    }
}
