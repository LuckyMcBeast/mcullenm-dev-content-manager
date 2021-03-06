package dev.mcullenm.contentmanager.controller

import dev.mcullenm.contentmanager.model.request.CreateBlogRequest
import dev.mcullenm.contentmanager.service.BlogService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/blog")
class BlogController(
    val blogService: BlogService
) {
    @CrossOrigin("*")
    @GetMapping
    fun getBlogs() = blogService.getBlogs()

    @CrossOrigin("*")
    @GetMapping("/{id}")
    fun getBlog(@PathVariable id: Int) = blogService.getBlog(id)

    @PostMapping
    fun postBlog(@RequestBody createBlogRequest: CreateBlogRequest) = blogService.postBlog(createBlogRequest)
}
