package dev.mcullenm.contentmanager.model

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import dev.mcullenm.contentmanager.repository.entity.BlogEntity
import java.time.LocalDate

data class Blog(
    @JsonProperty
    val blogId: Int?,
    @JsonProperty
    val title: String,
    @JsonProperty
    @JsonFormat(pattern = "MM-dd-yyyy")
    var publishDate: LocalDate?,
    @JsonIgnore
    val content: List<Content>
) {
    @JsonProperty("content")
    val sortedContent: List<Content> = content.sortedBy { it.position }

    companion object {
        fun fromEntity(blogEntity: BlogEntity, content: List<Content>): Blog {
            return Blog(
                blogId = blogEntity.id,
                title = blogEntity.title,
                publishDate = blogEntity.publishDate,
                content = content
            )
        }
    }

    fun providePublishDate() {
        if (publishDate == null) {
            publishDate = LocalDate.now()
        }
    }
}
