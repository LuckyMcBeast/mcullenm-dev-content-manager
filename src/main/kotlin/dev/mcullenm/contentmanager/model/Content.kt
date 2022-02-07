package dev.mcullenm.contentmanager.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.node.ObjectNode

data class Content(
    @JsonProperty
    val position: Int,
    @JsonProperty
    val type: String,
    @JsonProperty
    val value: String
) {
    companion object {
        fun from(node: ObjectNode): Content {
            return Content(
                node["position"].asInt(),
                node["type"].textValue(),
                node["value"].textValue()
            )
        }
    }
}
