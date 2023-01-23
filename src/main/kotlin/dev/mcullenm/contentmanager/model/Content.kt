package dev.mcullenm.contentmanager.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import dev.mcullenm.contentmanager.repository.entity.ContentEntity

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Content(
    @JsonProperty
    val position: Int,
    @JsonProperty
    val type: String,
    @JsonProperty
    val value: String,
    val meta: Meta? = null
) {
    companion object {
        fun fromEntity(contentEntity: ContentEntity): Content {
            return Content(
                position = contentEntity.position,
                type = contentEntity.type,
                value = contentEntity.value,
                meta = contentEntity.meta?.let { Meta.fromJson(it) }
            )
        }
    }
}

fun List<Content>.toContentEntityList(blogId: Int): List<ContentEntity> {
    return this.map { content -> ContentEntity.from(blogId, content) }
}
