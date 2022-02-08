package dev.mcullenm.contentmanager.datasource

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.whenever
import dev.mcullenm.contentmanager.model.Blog
import dev.mcullenm.contentmanager.model.Content
import dev.mcullenm.contentmanager.model.request.CreateBlogRequest
import dev.mcullenm.contentmanager.model.response.CreateBlogResponse
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity
import org.springframework.web.client.postForEntity

internal class FastApiBlogDataSourceTest {

    @InjectMocks
    private lateinit var fastApiBlogDataSource: FastApiBlogDataSource

    @Mock
    private lateinit var fastApiRestTemplate: RestTemplate

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `should retrieve collection of blogs`() {
        val responseString =
            """[{"blog_id":1,"title":"Test","publish_date":"2022-01-13 06:10:58.632369","content":[{"position":0,"type":"p","value":"This is some content"},{"position":1,"type":"p","value":"This is some content"}]},{"blog_id":2,"title":"Test","publish_date":"2022-01-13 06:10:58.632369","content":[{"position":0,"type":"p","value":"This is some content"},{"position":1,"type":"p","value":"This is some content"}]}]"""
        whenever(fastApiRestTemplate.getForEntity(any<String>(), eq(String::class.java)))
            .thenReturn(
                ResponseEntity(responseString, HttpStatus.OK)
            )
        val expected = mutableListOf(
            Blog
            (
                1, "Test", "2022-01-13 06:10:58.632369",
                listOf(
                    Content(0, "p", "This is some content"),
                    Content(1, "p", "This is some content")
                )
            ),
            Blog
            (
                2, "Test", "2022-01-13 06:10:58.632369",
                listOf(
                    Content(0, "p", "This is some content"),
                    Content(1, "p", "This is some content")
                )
            ),
        )

        val actual = fastApiBlogDataSource.retrieveBlogs()

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `should retrieve a blog by id`() {
        val responseString =
            """{"blog_id":1,"title":"Test","publish_date":"2022-01-13 06:10:58.632369","content":[{"position":0,"type":"p","value":"This is some content"},{"position":1,"type":"p","value":"This is some content"}]}"""
        whenever(fastApiRestTemplate.getForEntity(any<String>(), eq(String::class.java)))
            .thenReturn(
                ResponseEntity(responseString, HttpStatus.OK)
            )
        val expected = Blog(
            1, "Test", "2022-01-13 06:10:58.632369",
            listOf(
                Content(0, "p", "This is some content"),
                Content(1, "p", "This is some content")
            )
        )

        val actual = fastApiBlogDataSource.retrieveBlog(1)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `should create blog from createBlogRequest`() {
        val createBlogRequest = CreateBlogRequest("Test", listOf())
        val expected = CreateBlogResponse(true, 1, 0)
        whenever(fastApiRestTemplate.postForEntity(any<String>(), eq(createBlogRequest), eq(CreateBlogResponse::class.java)))
            .thenReturn(
                ResponseEntity(expected, HttpStatus.OK)
            )

        val actual = fastApiBlogDataSource.createBlog(createBlogRequest)

        assertThat(actual).isEqualTo(expected)
    }
}
