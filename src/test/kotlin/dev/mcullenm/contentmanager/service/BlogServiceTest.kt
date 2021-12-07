package dev.mcullenm.contentmanager.service

import dev.mcullenm.contentmanager.datasource.BlogDataSource
import dev.mcullenm.contentmanager.datasource.MockBlogDataSource
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

internal class BlogServiceTest{

    @InjectMocks
    lateinit var blogService: BlogService

    @Mock
    lateinit var mockBlogDataSource: MockBlogDataSource

    @BeforeEach
    fun setup(){
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `should getBlogs from Datasource`(){
        blogService.getBlogs()

        verify(mockBlogDataSource).retrieveBlogs()
    }

}