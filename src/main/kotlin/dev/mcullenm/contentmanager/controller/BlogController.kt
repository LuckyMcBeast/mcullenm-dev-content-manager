package dev.mcullenm.contentmanager.controller

import dev.mcullenm.contentmanager.service.BlogService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/blogs")
class BlogController(
    val blogService: BlogService
) {
    @CrossOrigin("*")
    @GetMapping
    fun getBlogs() = blogService.getBlogs()
}
