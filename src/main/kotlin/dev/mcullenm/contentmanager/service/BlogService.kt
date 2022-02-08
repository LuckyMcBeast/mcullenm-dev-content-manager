package dev.mcullenm.contentmanager.service

import dev.mcullenm.contentmanager.datasource.BlogDataSource
import org.springframework.stereotype.Service

@Service
class BlogService(
    val blogDataSource: BlogDataSource
) {
    fun getBlogs() = blogDataSource.retrieveBlogs()
    fun getBlog(id: Int) = blogDataSource.retrieveBlog(id)
}
