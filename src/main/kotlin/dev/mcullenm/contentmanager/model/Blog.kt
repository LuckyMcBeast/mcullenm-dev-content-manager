package dev.mcullenm.contentmanager.model

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.node.ObjectNode

data class Blog(
    @JsonProperty
    @JsonAlias("blog_id")
    val blogId: Int,
    @JsonProperty
    val title: String,
    @JsonProperty
    @JsonAlias("publish_date")
    val publishDate: String,
    @JsonProperty
    val content: List<Content>,
) {
    companion object {
        fun from(node: ObjectNode): Blog {
            val contentList: MutableList<Content> = mutableListOf()
            for (content in node["content"]) {
                contentList.add(Content.from(content as ObjectNode))
            }
            return Blog(
                node["blog_id"].asInt(),
                node["title"].textValue(),
                node["publish_date"].textValue(),
                contentList
            )
        }
    }
}
