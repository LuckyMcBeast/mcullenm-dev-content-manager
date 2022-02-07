package dev.mcullenm.contentmanager.datasource

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import dev.mcullenm.contentmanager.model.Blog
import org.springframework.context.annotation.Primary
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity

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
}
