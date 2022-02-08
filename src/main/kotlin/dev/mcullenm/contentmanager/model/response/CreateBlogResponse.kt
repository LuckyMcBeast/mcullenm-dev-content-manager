package dev.mcullenm.contentmanager.model.response

import com.fasterxml.jackson.annotation.JsonAlias

data class CreateBlogResponse(
    val success: Boolean,
    @JsonAlias("created_id")
    val createdId: Int,
    @JsonAlias("content_amount")
    val contentAmount: Int
)
