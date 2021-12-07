package dev.mcullenm.contentmanager.controller

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest
@AutoConfigureMockMvc
internal class BlogControllerTest @Autowired constructor(
    private val mockMvc: MockMvc
){
    private val blogsEndpoint = "/api/blogs"

    @Test
    fun `should return list of all blogs`(){
        mockMvc.get(blogsEndpoint)
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content { MediaType.APPLICATION_JSON }
                jsonPath("$[0].blogId") {value(1)}
                jsonPath("$[1].blogId") {value(2)}
                jsonPath("$[2].blogId") {value(3)}
                jsonPath("$[3].blogId") {value(4)}
            }
    }
}