package dev.mcullenm.contentmanager.service

import dev.mcullenm.contentmanager.model.request.CreateBlogRequest
import dev.mcullenm.contentmanager.model.response.CreateBlogResponse
import dev.mcullenm.contentmanager.repository.BlogDataSource
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class BlogService(
    val blogDataSource: BlogDataSource
) {
    fun getBlogs() = blogDataSource.retrieveBlogs()
    fun getBlog(id: Int) = blogDataSource.retrieveBlog(id)
    fun postBlog(createBlogRequest: CreateBlogRequest, creationDate: LocalDate?): CreateBlogResponse {
        creationDate?.let {
            return blogDataSource.createBlog(createBlogRequest.toBlog(it))
        }
        return blogDataSource.createBlog(createBlogRequest.toBlog())
    }

    fun updateBlog(id: Int, createBlogRequest: CreateBlogRequest): CreateBlogResponse {
        createBlogRequest.toBlog().let { blog ->
            blogDataSource.updateBlog(id, blog).let { response ->
                if (response.success) {
                    return response
                }
                return blogDataSource.createBlog(blog.copy(blogId = id))
            }
        }
    }

    fun deleteBlog(id: Int) = blogDataSource.removeBlog(id)
}
