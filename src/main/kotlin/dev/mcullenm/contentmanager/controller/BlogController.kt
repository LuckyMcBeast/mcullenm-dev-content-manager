package dev.mcullenm.contentmanager.controller

import dev.mcullenm.contentmanager.model.request.CreateBlogRequest
import dev.mcullenm.contentmanager.model.response.CreateBlogResponse
import dev.mcullenm.contentmanager.model.response.DeleteBlogResponse
import dev.mcullenm.contentmanager.service.BlogService
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RestController
@RequestMapping("/blogs")
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
    fun postBlog(
        @RequestBody createBlogRequest: CreateBlogRequest,
        @RequestParam creationDate: String? = null
    ): CreateBlogResponse? {
        try {
            return blogService.postBlog(
                createBlogRequest,
                creationDate?.let { LocalDate.parse(creationDate, DateTimeFormatter.ISO_LOCAL_DATE) }
            )
        } catch (e: Exception) {
            throw e
        }
    }

    @PutMapping("/{id}")
    fun putBlog(@PathVariable id: Int, @RequestBody createBlogRequest: CreateBlogRequest) =
        blogService.updateBlog(id, createBlogRequest)

    @DeleteMapping("/{id}")
    fun deleteBlog(@PathVariable id: Int): DeleteBlogResponse = blogService.deleteBlog(id)
}
