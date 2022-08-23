package dev.mcullenm.contentmanager.model.request

import dev.mcullenm.contentmanager.model.Blog
import dev.mcullenm.contentmanager.model.Content
import java.time.LocalDate
import java.util.*

data class CreateBlogRequest(
    val title: String,
    val content: List<Content>
) {
    fun toBlog(publishDate: LocalDate? = null): Blog {
        return Blog(
            blogId = null,
            title = title,
            publishDate = publishDate,
            content = content
        )
    }
}
