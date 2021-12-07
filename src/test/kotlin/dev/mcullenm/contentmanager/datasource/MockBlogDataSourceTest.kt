package dev.mcullenm.contentmanager.datasource

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

internal class MockBlogDataSourceTest{

    @Mock
    lateinit var mockBlogDataSource: MockBlogDataSource

    @BeforeEach
    fun setup(){
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `should retrieve blogs`(){
        val actual = mockBlogDataSource.retrieveBlogs()

        assertThat(mockBlogDataSource.blogs).isEqualTo(actual)
    }
}