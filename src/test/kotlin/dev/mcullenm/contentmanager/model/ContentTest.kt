package dev.mcullenm.contentmanager.model

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test

internal class ContentTest {

    @Test
    fun `should create from ObjectNode`() {
        val objectNode = ObjectMapper().readValue("""{"position":0,"type":"p","value":"This is some content"}""", ObjectNode::class.java)
        val expected = Content(0, "p", "This is some content")

        val actual = Content.from(objectNode)

        assertThat(actual).isEqualTo(expected)
    }
}
