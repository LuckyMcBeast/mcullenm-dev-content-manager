package dev.mcullenm.contentmanager.repository

import dev.mcullenm.contentmanager.model.Blog
import dev.mcullenm.contentmanager.model.response.CreateBlogResponse
import dev.mcullenm.contentmanager.model.response.DeleteBlogResponse

interface BlogDataSource {
    fun retrieveBlogs(): Collection<Blog>?
    fun retrieveBlog(id: Int): Blog?
    fun createBlog(blog: Blog): CreateBlogResponse
    fun updateBlog(id: Int, blog: Blog): CreateBlogResponse
    fun removeBlog(id: Int): DeleteBlogResponse
}
