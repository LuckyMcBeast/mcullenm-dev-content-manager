package dev.mcullenm.contentmanager.helper

import dev.mcullenm.contentmanager.model.Blog
import dev.mcullenm.contentmanager.model.toContentEntityList
import dev.mcullenm.contentmanager.repository.entity.BlogEntity
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

fun TestEntityManager.createBlog(blog: Blog): Int {
    this.persistAndGetId(BlogEntity.from(blog)).let { blogId ->
        blog.content.toContentEntityList(blogId as Int).forEach { contentEntity ->
            this.persist(contentEntity)
        }
        this.flush()
        return blogId
    }
}
