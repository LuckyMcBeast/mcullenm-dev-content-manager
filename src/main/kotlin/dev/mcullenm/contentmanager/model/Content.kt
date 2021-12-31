package dev.mcullenm.contentmanager.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Content(
    @JsonProperty
    val position: Int,
    @JsonProperty
    val type: String,
    @JsonProperty
    val value: String
)