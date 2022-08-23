package dev.mcullenm.contentmanager.model

import com.fasterxml.jackson.annotation.JsonProperty
import dev.mcullenm.contentmanager.repository.entity.ContentEntity

data class Content(
    @JsonProperty
    val position: Int,
    @JsonProperty
    val type: String,
    @JsonProperty
    val value: String
) {
    companion object {
        fun fromEntity(contentEntity: ContentEntity): Content {
            return Content(
                position = contentEntity.position,
                type = contentEntity.type,
                value = contentEntity.value
            )
        }
    }
}

fun List<Content>.toContentEntityList(blogId: Int): List<ContentEntity> {
    return this.map { content -> ContentEntity.from(blogId, content) }
}
