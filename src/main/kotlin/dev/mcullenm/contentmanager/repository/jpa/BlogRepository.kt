package dev.mcullenm.contentmanager.repository.jpa

import dev.mcullenm.contentmanager.repository.entity.BlogEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate

interface BlogRepository : JpaRepository<BlogEntity, Int> {
    @Query(value = "select setval('blog_seq', 1)", nativeQuery = true)
    fun resetSequence(): Int // Used in testing only

    @Modifying
    @Query(value = "insert into blog (publish_date, title, id) values (:publishDate, :title, :id)", nativeQuery = true)
    fun saveWithId(
        publishDate: LocalDate,
        title: String,
        id: Int
    ): Int
}
