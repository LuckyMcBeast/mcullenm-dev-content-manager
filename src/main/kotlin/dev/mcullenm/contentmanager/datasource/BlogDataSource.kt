package dev.mcullenm.contentmanager.datasource

import dev.mcullenm.contentmanager.model.Blog
import dev.mcullenm.contentmanager.model.request.CreateBlogRequest
import dev.mcullenm.contentmanager.model.response.CreateBlogResponse

interface BlogDataSource {
    fun retrieveBlogs(): Collection<Blog>?
    fun retrieveBlog(id: Int): Blog?
    fun createBlog(createBlogRequest: CreateBlogRequest): CreateBlogResponse?
}
