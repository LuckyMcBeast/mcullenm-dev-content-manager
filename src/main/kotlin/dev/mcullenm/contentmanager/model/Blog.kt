package dev.mcullenm.contentmanager.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Blog(
    @JsonProperty
    val blogId: Int,
    @JsonProperty
    val title: String,
    @JsonProperty
    val publishDate: String,
    @JsonProperty
    val content: List<Content>,
)