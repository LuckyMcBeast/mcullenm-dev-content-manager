package dev.mcullenm.contentmanager.model.response

data class CreateBlogResponse(
    val success: Boolean,
    val createdId: Int,
    val contentAmount: Int
)
