package dev.mcullenm.contentmanager.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.whenever
import dev.mcullenm.contentmanager.model.Blog
import dev.mcullenm.contentmanager.model.Content
import dev.mcullenm.contentmanager.model.request.CreateBlogRequest
import dev.mcullenm.contentmanager.model.response.CreateBlogResponse
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
internal class BlogControllerIntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    private val blogsEndpoint = "/api/blog"

    @MockBean
    private lateinit var fastApiRestTemplate: RestTemplate

    @Test
    fun `should return list of all blogs`() {
        val responseString =
            """[{"blog_id":1,"title":"Test","publish_date":"2022-01-13 06:10:58.632369","content":[{"position":0,"type":"p","value":"This is some content"},{"position":1,"type":"p","value":"This is some content"}]},{"blog_id":2,"title":"Test","publish_date":"2022-01-13 06:10:58.632369","content":[{"position":0,"type":"p","value":"This is some content"},{"position":1,"type":"p","value":"This is some content"}]}]"""
        val blogCollection = mutableListOf<Blog>()
        whenever(fastApiRestTemplate.getForEntity(any<String>(), eq(String::class.java)))
            .thenReturn(
                ResponseEntity(responseString, HttpStatus.OK)
            )

        val getBlogsResponse = mockMvc.get(blogsEndpoint)
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content { MediaType.APPLICATION_JSON }
                jsonPath("$[0].blogId") { value(1) }
                jsonPath("$[1].blogId") { value(2) }
            }
            .andReturn().response

        val blogsJsonNode = ObjectMapper().readTree(getBlogsResponse.contentAsString)

        for (node in blogsJsonNode) {
            blogCollection.add(Blog.fromResponse(node as ObjectNode))
        }

        assertThat(blogCollection[0]).isInstanceOf(Blog::class.java)
        assertThat(blogCollection.size).isEqualTo(2)
    }

    @Test
    fun `should return blog`() {
        val responseString =
            """{"blog_id":1,"title":"Test","publish_date":"2022-01-13 06:10:58.632369","content":[{"position":0,"type":"p","value":"This is some content"},{"position":1,"type":"p","value":"This is some content"}]}"""
        whenever(fastApiRestTemplate.getForEntity(any<String>(), eq(String::class.java)))
            .thenReturn(
                ResponseEntity(responseString, HttpStatus.OK)
            )

        val getBlogResponse = mockMvc.get("$blogsEndpoint/1")
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content { MediaType.APPLICATION_JSON }
                jsonPath("$.blogId") { value(1) }
            }
            .andReturn().response

        val blog = Blog.fromResponse(ObjectMapper().readTree(getBlogResponse.contentAsString) as ObjectNode)

        assertThat(blog).isEqualTo(
            Blog
            (
                1, "Test", "2022-01-13 06:10:58.632369",
                listOf(
                    Content(0, "p", "This is some content"),
                    Content(1, "p", "This is some content")
                )
            )
        )
    }

    @Test
    fun `should create blog`() {
        val requestString = """{"title": "Test", "content": [{"position": 0, "type": "p","value": "This is some content"},{"position": 1, "type": "p", "value": "This is some content"}]}"""
        val response = CreateBlogResponse(true, 1, 2)
        whenever(fastApiRestTemplate.postForEntity(any<String>(), any<CreateBlogRequest>(), eq(CreateBlogResponse::class.java)))
            .thenReturn(
                ResponseEntity(response, HttpStatus.OK)
            )

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

        assertThat(response).isEqualTo(createBlogResponse)
    }

    private fun ObjectNode.createBlogResponseObject(): CreateBlogResponse {
        return CreateBlogResponse(
            success = this["success"].asBoolean(),
            createdId = this["createdId"].asInt(),
            contentAmount = this["contentAmount"].asInt()
        )
    }

    private fun Blog.Companion.fromResponse(node: ObjectNode): Blog {
        val contentList: MutableList<Content> = mutableListOf()
        for (content in node["content"]) {
            contentList.add(Content.from(content as ObjectNode))
        }
        return Blog(
            node["blogId"].asInt(),
            node["title"].textValue(),
            node["publishDate"].textValue(),
            contentList
        )
    }
}
