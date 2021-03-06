package dev.mcullenm.contentmanager.service

import dev.mcullenm.contentmanager.datasource.MockBlogDataSource
import dev.mcullenm.contentmanager.model.Blog
import dev.mcullenm.contentmanager.model.request.CreateBlogRequest
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean

@SpringBootTest
internal class BlogServiceTest {

    @Qualifier("Mock")
    lateinit var blogService: BlogService

    @MockBean
    @Autowired
    lateinit var mockBlogDataSource: MockBlogDataSource

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
        blogService = BlogService(mockBlogDataSource)
    }

    @Test
    fun `should getBlogs from Datasource`() {
        val actual = blogService.getBlogs()

        verify(mockBlogDataSource).retrieveBlogs()
        assertThat(actual).isEqualTo(listOf<Blog>())
    }

    @Test
    fun `should getBlog from Datasource`() {
        blogService.getBlog(1)

        verify(mockBlogDataSource).retrieveBlog(1)
    }

    @Test
    fun `should createBlog via Datasource`() {
        val createBlogRequest = CreateBlogRequest("Test", listOf())
        blogService.postBlog(createBlogRequest)

        verify(mockBlogDataSource).createBlog(createBlogRequest)
    }
}
