package dev.mcullenm.contentmanager.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import dev.mcullenm.contentmanager.model.Content
import dev.mcullenm.contentmanager.model.Meta
import dev.mcullenm.contentmanager.model.request.CreateBlogRequest
import dev.mcullenm.contentmanager.model.response.CreateBlogResponse
import dev.mcullenm.contentmanager.model.response.DeleteBlogResponse
import dev.mcullenm.contentmanager.repository.BlogRepositoryAdapter
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*
import java.time.LocalDate

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Uses and requires active PostgresDB
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
internal class BlogControllerIntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var blogRepositoryAdapter: BlogRepositoryAdapter

    private val blogsEndpoint = "/blogs"

    @BeforeEach
    @AfterEach
    fun resetDatabase() {
        blogRepositoryAdapter.blogRepository.deleteAll()
        blogRepositoryAdapter.contentRepository.deleteAll()
        blogRepositoryAdapter.blogRepository.resetSequence() // next blogId will be 2
    }

    @Test
    fun `should return list of all blogs`() {
        blogRepositoryAdapter.createBlog(
            CreateBlogRequest(
                title = "Test",
                content = listOf(Content(position = 1, type = "p", "This is some content"))
            ).toBlog(
                LocalDate.parse("2022-01-13")
            )
        )
        blogRepositoryAdapter.createBlog(
            CreateBlogRequest(
                title = "Test",
                content = listOf(Content(position = 1, type = "p", "This is some content"))
            ).toBlog(
                LocalDate.parse("2022-01-13")
            )
        )

        val responseString =
            """[{"blogId":2,"title":"Test","publishDate":"01-13-2022","content":[{"position":1,"type":"p","value":"This is some content"}]},{"blogId":3,"title":"Test","publishDate":"01-13-2022","content":[{"position":1,"type":"p","value":"This is some content"}]}]"""

        val getBlogsResponse = mockMvc.get(blogsEndpoint)
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content { MediaType.APPLICATION_JSON }
                jsonPath("$[0].blogId") { value(2) }
                jsonPath("$[1].blogId") { value(3) }
            }
            .andReturn().response.contentAsString

        println(getBlogsResponse)
        assertThat(getBlogsResponse).isEqualTo(responseString)
    }

    @Test
    fun `should return blog`() {
        blogRepositoryAdapter.createBlog(
            CreateBlogRequest(
                title = "Test",
                content = listOf(Content(position = 1, type = "p", "This is some content"))
            ).toBlog(
                LocalDate.parse("2022-01-13")
            )
        )

        val responseString =
            """{"blogId":2,"title":"Test","publishDate":"01-13-2022","content":[{"position":1,"type":"p","value":"This is some content"}]}"""

        val getBlogResponse = mockMvc.get("$blogsEndpoint/2")
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content { MediaType.APPLICATION_JSON }
                jsonPath("$.blogId") { value(2) }
            }
            .andReturn().response.contentAsString

        assertThat(getBlogResponse).isEqualTo(responseString)
    }

    @Test
    fun `should return blog with code content containing language metadata`() {
        blogRepositoryAdapter.createBlog(
            CreateBlogRequest(
                title = "Test",
                content = listOf(Content(position = 1, type = "code", value = """val test : String = "test"""", meta = Meta(lang = "kotlin")))
            ).toBlog(
                LocalDate.parse("2022-01-13")
            )
        )

        val responseString =
            """{"blogId":2,"title":"Test","publishDate":"01-13-2022","content":[{"position":1,"type":"code","value":"val test : String = \"test\"","meta":{"lang":"kotlin"}}]}"""

        val getBlogResponse = mockMvc.get("$blogsEndpoint/2")
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content { MediaType.APPLICATION_JSON }
                jsonPath("$.blogId") { value(2) }
            }
            .andReturn().response.contentAsString

        assertThat(getBlogResponse).isEqualTo(responseString)
    }

    @Test
    fun `should return blog with link (a) content containing altText`() {
        blogRepositoryAdapter.createBlog(
            CreateBlogRequest(
                title = "Test",
                content = listOf(Content(position = 1, type = "a", value = "https://mcullenm.dev", meta = Meta(altText = "This Blog")))
            ).toBlog(
                LocalDate.parse("2022-01-13")
            )
        )

        val responseString =
            """{"blogId":2,"title":"Test","publishDate":"01-13-2022","content":[{"position":1,"type":"a","value":"https://mcullenm.dev","meta":{"altText":"This Blog"}}]}"""

        val getBlogResponse = mockMvc.get("$blogsEndpoint/2")
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content { MediaType.APPLICATION_JSON }
                jsonPath("$.blogId") { value(2) }
            }
            .andReturn().response.contentAsString

        assertThat(getBlogResponse).isEqualTo(responseString)
    }

    @Test
    fun `should create blog`() {
        val requestString =
            """{"title": "Test", "content": [{"position": 0, "type": "p","value": "This is some content"},{"position": 1, "type": "p", "value": "This is some content"}]}"""
        val expectedResponse = CreateBlogResponse(true, 2, 2)

        val postBlogResponse = mockMvc.post(blogsEndpoint) {
            content = requestString
            contentType = MediaType.APPLICATION_JSON
        }
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content { MediaType.APPLICATION_JSON }
                jsonPath("$.success") { value(true) }
            }
            .andReturn().response.contentAsString

        val createBlogResponse = (ObjectMapper().readTree(postBlogResponse) as ObjectNode).createBlogResponseObject()

        assertThat(createBlogResponse).isEqualTo(expectedResponse)
    }

    @Test
    fun `should update blog`() {
        val requestString =
            """{"title": "Test", "content": [{"position": 0, "type": "p","value": "This is some content"},{"position": 1, "type": "p", "value": "This is some content"}]}"""
        val expectedResponse = CreateBlogResponse(true, 2, 2)

        blogRepositoryAdapter.createBlog(
            CreateBlogRequest(
                title = "Test",
                content = listOf(Content(position = 1, type = "p", "This is some content"))
            ).toBlog(
                LocalDate.parse("2022-01-13")
            )
        )

        val putBlogResponse = mockMvc.put("$blogsEndpoint/2") {
            content = requestString
            contentType = MediaType.APPLICATION_JSON
        }
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content { MediaType.APPLICATION_JSON }
                jsonPath("$.success") { value(true) }
            }
            .andReturn().response.contentAsString

        val updateBlogResponse = (ObjectMapper().readTree(putBlogResponse) as ObjectNode).createBlogResponseObject()

        assertThat(updateBlogResponse).isEqualTo(expectedResponse)
    }

    @Test
    fun `should create blog if blog if blog does not exist`() {
        val requestString =
            """{"title": "Test", "content": [{"position": 0, "type": "p","value": "This is some content"},{"position": 1, "type": "p", "value": "This is some content"}]}"""
        val expectedResponse = CreateBlogResponse(true, 100, 2)

        val putBlogResponse = mockMvc.put("$blogsEndpoint/100") {
            content = requestString
            contentType = MediaType.APPLICATION_JSON
        }
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content { MediaType.APPLICATION_JSON }
                jsonPath("$.success") { value(true) }
            }
            .andReturn().response.contentAsString

        val updateBlogResponse = (ObjectMapper().readTree(putBlogResponse) as ObjectNode).createBlogResponseObject()

        assertThat(updateBlogResponse).isEqualTo(expectedResponse)
    }

    @Test
    fun `should delete blog`() {
        val expectedResponse = DeleteBlogResponse(true, 2)

        blogRepositoryAdapter.createBlog(
            CreateBlogRequest(
                title = "Test",
                content = listOf(Content(position = 1, type = "p", "This is some content"))
            ).toBlog(
                LocalDate.parse("2022-01-13")
            )
        )

        val deleteBlogResponse = mockMvc.delete("$blogsEndpoint/2")
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content { MediaType.APPLICATION_JSON }
                jsonPath("$.success") { value(true) }
                jsonPath("$.deletedId") { value(2) }
            }
            .andReturn().response.contentAsString

        val removeBlogResponse = (ObjectMapper().readTree(deleteBlogResponse) as ObjectNode).deleteBlogResponseObject()

        assertThat(removeBlogResponse).isEqualTo(expectedResponse)
        assertThat(blogRepositoryAdapter.retrieveBlog(2)).isNull()
        assertThat(blogRepositoryAdapter.contentRepository.findAllContentByBlogId(2).size).isEqualTo(0)
    }

    private fun ObjectNode.deleteBlogResponseObject(): DeleteBlogResponse {
        return DeleteBlogResponse(
            success = this["success"].asBoolean(),
            deletedId = this["deletedId"].asInt()
        )
    }

    private fun ObjectNode.createBlogResponseObject(): CreateBlogResponse {
        return CreateBlogResponse(
            success = this["success"].asBoolean(),
            createdId = this["createdId"].asInt(),
            contentAmount = this["contentAmount"].asInt()
        )
    }
}
