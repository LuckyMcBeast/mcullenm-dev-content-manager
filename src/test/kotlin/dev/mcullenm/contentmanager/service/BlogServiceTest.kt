package dev.mcullenm.contentmanager.service

import dev.mcullenm.contentmanager.datasource.MockBlogDataSource
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
        blogService.getBlogs()

        verify(mockBlogDataSource).retrieveBlogs()
    }
}
