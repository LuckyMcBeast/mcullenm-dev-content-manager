package dev.mcullenm.contentmanager.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class MetaTest {
    @Test
    fun `should convert to Json string`() {
        val expected = """{"lang":"kotlin"}"""

        val actual = Meta(lang = "kotlin").toJsonString()

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `should convert to Json string when only has altText`() {
        val expected = """{"altText":"test"}"""

        val actual = Meta(altText = "test").toJsonString()

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `should convert to Meta object from Json string`() {
        val expected = Meta(lang = "kotlin", altText = "test")

        val actual = Meta.fromJson("""{"lang":"kotlin","altText":"test"}""")

        assertThat(actual).isEqualTo(expected)
    }
}
