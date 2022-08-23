package dev.mcullenm.contentmanager.service

import dev.mcullenm.contentmanager.model.Blog
import dev.mcullenm.contentmanager.model.request.CreateBlogRequest
import dev.mcullenm.contentmanager.repository.BlogRepositoryAdapter
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean

@SpringBootTest
internal class BlogServiceTest {

    lateinit var blogService: BlogService

    @MockBean
    lateinit var blogRepositoryAdapter: BlogRepositoryAdapter

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
        blogService = BlogService(blogRepositoryAdapter)
    }

    @Test
    fun `should getBlogs from Datasource`() {
        val actual = blogService.getBlogs()

        verify(blogRepositoryAdapter).retrieveBlogs()
        assertThat(actual).isEqualTo(listOf<Blog>())
    }

    @Test
    fun `should getBlog from Datasource`() {
        blogService.getBlog(1)

        verify(blogRepositoryAdapter).retrieveBlog(1)
    }

    @Test
    fun `should createBlog via Datasource`() {
        val createBlogRequest = CreateBlogRequest("Test", listOf())
        blogService.postBlog(createBlogRequest, null)

        verify(blogRepositoryAdapter).createBlog(createBlogRequest.toBlog())
    }

    @Test
    fun `should update blog via Datasource`() {
        val createBlogRequest = CreateBlogRequest("Test", listOf())
        blogService.updateBlog(1, createBlogRequest)

        verify(blogRepositoryAdapter).updateBlog(1, createBlogRequest.toBlog())
    }

    @Test
    fun `should delete blog via Datasource`() {
        blogService.deleteBlog(1)

        verify(blogRepositoryAdapter).removeBlog(1)
    }
}
