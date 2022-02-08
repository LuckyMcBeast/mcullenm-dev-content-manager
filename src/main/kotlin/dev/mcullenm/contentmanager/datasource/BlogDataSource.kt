package dev.mcullenm.contentmanager.datasource

import dev.mcullenm.contentmanager.model.Blog

interface BlogDataSource {
    fun retrieveBlogs(): Collection<Blog>?
    fun retrieveBlog(id: Int): Blog?
}
