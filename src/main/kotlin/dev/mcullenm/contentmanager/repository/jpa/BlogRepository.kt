package dev.mcullenm.contentmanager.repository.jpa

import dev.mcullenm.contentmanager.repository.entity.BlogEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface BlogRepository : JpaRepository<BlogEntity, Int> {
    @Query(value = "select setval('blog_seq', 1)", nativeQuery = true)
    fun resetSequence(): Int // Used in testing only
}
