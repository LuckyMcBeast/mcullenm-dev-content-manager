package dev.mcullenm.contentmanager.repository.jpa

import dev.mcullenm.contentmanager.repository.entity.ContentEntity
import dev.mcullenm.contentmanager.repository.entity.ContentEntityId
import org.springframework.data.jpa.repository.JpaRepository

interface ContentRepository : JpaRepository<ContentEntity, ContentEntityId> {
    fun findAllContentByBlogId(blogId: Int): List<ContentEntity>
    fun deleteAllContentByBlogId(blogId: Int)
}
