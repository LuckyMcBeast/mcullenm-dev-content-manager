package dev.mcullenm.contentmanager.repository.entity

import dev.mcullenm.contentmanager.model.Content
import org.hibernate.Hibernate
import java.io.Serializable
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.IdClass
import javax.persistence.Table

data class ContentEntityId(
    val blogId: Int = 0,
    val position: Int = 0
) : Serializable

@Entity
@Table(name = "content")
@IdClass(ContentEntityId::class)
data class ContentEntity(
    @Id val blogId: Int = 0,
    @Id val position: Int = 0,
    val type: String,
    @Column(columnDefinition = "TEXT")
    val value: String,
    @Column(columnDefinition = "TEXT")
    val meta: String? = null
) {
    companion object {
        fun from(blogId: Int, content: Content): ContentEntity {
            return ContentEntity(
                blogId = blogId,
                position = content.position,
                type = content.type,
                value = content.value,
                meta = content.meta?.toJsonString()
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as ContentEntity

        return blogId == other.blogId &&
            position == other.position
    }

    override fun hashCode(): Int = Objects.hash(blogId, position)

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(blogId = $blogId , position = $position , type = $type , value = $value, meta = $meta )"
    }
}

fun List<ContentEntity>.toContentList(): List<Content> {
    return this.map { contentEntity -> Content.fromEntity(contentEntity) }
}
