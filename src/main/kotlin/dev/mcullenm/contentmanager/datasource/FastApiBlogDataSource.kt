package dev.mcullenm.contentmanager.datasource

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import dev.mcullenm.contentmanager.model.Blog
import dev.mcullenm.contentmanager.model.request.CreateBlogRequest
import dev.mcullenm.contentmanager.model.response.CreateBlogResponse
import org.springframework.context.annotation.Primary
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity
import org.springframework.web.client.postForEntity

@Primary
@Repository("FastApi")
class FastApiBlogDataSource(
    val fastApiRestTemplate: RestTemplate,
) : BlogDataSource {
    override fun retrieveBlogs(): Collection<Blog>? {
        val blogList = mutableListOf<Blog>()
        val response: ResponseEntity<String> = fastApiRestTemplate.getForEntity("/blog/view")
        val nodes = ObjectMapper().readTree(response.body)
        for (node in nodes) {
            blogList.add(Blog.from(node as ObjectNode))
        }
        return blogList
    }

    override fun retrieveBlog(id: Int): Blog? {
        val response = fastApiRestTemplate.getForEntity("blog/$id/view", String::class.java)
        return Blog.from(ObjectMapper().readTree(response.body) as ObjectNode)
    }

    override fun createBlog(createBlogRequest: CreateBlogRequest): CreateBlogResponse? {
        val response: ResponseEntity<CreateBlogResponse> = fastApiRestTemplate.postForEntity("/blog", createBlogRequest)
        return response.body
    }
}
