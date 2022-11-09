package dev.mcullenm.contentmanager.repository.entity

import dev.mcullenm.contentmanager.model.Blog
import org.hibernate.Hibernate
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "blog")
data class BlogEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "blog_seq")
    @SequenceGenerator(name = "blog_seq", allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false)
    var id: Int? = null,
    var title: String,
    @CreatedDate
    @Column(updatable = false, nullable = false)
    var publishDate: LocalDate? = null
) {
    companion object {
        fun from(blog: Blog): BlogEntity {
            return BlogEntity(
                title = blog.title,
                publishDate = blog.publishDate
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as BlogEntity

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , title = $title , publishDate = $publishDate )"
    }
}
