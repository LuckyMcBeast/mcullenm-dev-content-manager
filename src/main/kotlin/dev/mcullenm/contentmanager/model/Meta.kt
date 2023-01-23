package dev.mcullenm.contentmanager.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Meta(
    val lang: String? = null,
    val altText: String? = null
) {
    fun toJsonString(): String {
        return ObjectMapper().writeValueAsString(this)
    }

    companion object {
        fun fromJson(jsonString: String): Meta {
            return ObjectMapper().readValue(jsonString, Meta::class.java)
        }
    }
}
