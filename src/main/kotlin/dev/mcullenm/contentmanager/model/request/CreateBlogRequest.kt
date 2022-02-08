package dev.mcullenm.contentmanager.model.request

import dev.mcullenm.contentmanager.model.Content

data class CreateBlogRequest(
    val title: String,
    val content: List<Content>
)
