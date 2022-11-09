package dev.mcullenm.contentmanager.repository

import dev.mcullenm.contentmanager.FailureResponse
import dev.mcullenm.contentmanager.NullFieldException
import dev.mcullenm.contentmanager.model.Blog
import dev.mcullenm.contentmanager.model.response.CreateBlogResponse
import dev.mcullenm.contentmanager.model.response.DeleteBlogResponse
import dev.mcullenm.contentmanager.model.toContentEntityList
import dev.mcullenm.contentmanager.repository.entity.BlogEntity
import dev.mcullenm.contentmanager.repository.entity.toContentList
import dev.mcullenm.contentmanager.repository.jpa.BlogRepository
import dev.mcullenm.contentmanager.repository.jpa.ContentRepository
import org.springframework.context.annotation.Primary
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Primary
@Repository
class BlogRepositoryAdapter(
    val blogRepository: BlogRepository,
    val contentRepository: ContentRepository
) : BlogDataSource {

    override fun retrieveBlogs(): Collection<Blog>? {
        val blogEntities = blogRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))
        if (blogEntities.isEmpty()) {
            return null
        }
        return blogEntities.map { blogEntity ->
            mapEntitiesToBlog(blogEntity)
        }
    }

    override fun retrieveBlog(id: Int): Blog? {
        return blogRepository.findByIdOrNull(id)?.let { blogEntity ->
            mapEntitiesToBlog(blogEntity)
        }
    }

    @Transactional
    override fun createBlog(blog: Blog): CreateBlogResponse {
        try {
            blog.providePublishDate()
            blog.blogId?.let {
                return createBlogWithProvidedId(blog)
            }
            return createBlogWithSeqId(blog)
        } catch (e: Exception) {
            throw FailureResponse("CREATE_BLOG", e.message ?: "", e)
        }
    }

    @Transactional
    private fun createBlogWithSeqId(blog: Blog): CreateBlogResponse {
        return blogRepository.save(BlogEntity.from(blog)).id?.let { blogId ->
            CreateBlogResponse(
                success = true,
                createdId = blogId,
                contentAmount = contentRepository.saveAll(blog.content.toContentEntityList(blogId)).size
            )
        } ?: throw NullFieldException("blog.id", BlogRepositoryAdapter::createBlog.toString())
    }

    @Transactional
    private fun createBlogWithProvidedId(blog: Blog): CreateBlogResponse {
        blogRepository.saveWithId(blog.publishDate!!, blog.title, blog.blogId!!)
        return blogRepository.findByIdOrNull(blog.blogId)?.id?.let { blogId ->
            CreateBlogResponse(
                success = true,
                createdId = blogId,
                contentAmount = contentRepository.saveAll(blog.content.toContentEntityList(blogId)).size
            )
        } ?: throw Exception("Failed to create blog with specified ID")
    }

    @Transactional
    override fun updateBlog(id: Int, blog: Blog): CreateBlogResponse {
        try {
            blogRepository.findByIdOrNull(id)?.let { blogEntity ->
                blogEntity.title = blog.title
                contentRepository.deleteAllContentByBlogId(id)
                blogRepository.save(blogEntity)
                return CreateBlogResponse(
                    success = true,
                    createdId = id,
                    contentAmount = contentRepository.saveAll(blog.content.toContentEntityList(id)).size
                )
            } ?: return CreateBlogResponse(
                success = false,
                createdId = id,
                contentAmount = 0
            )
        } catch (e: Exception) {
            throw FailureResponse("UPDATE_BLOG", e.message ?: "", e)
        }
    }

    @Transactional
    override fun removeBlog(id: Int): DeleteBlogResponse {
        try {
            blogRepository.deleteById(id)
            contentRepository.deleteAllContentByBlogId(id)
            return DeleteBlogResponse(
                success = true,
                deletedId = id
            )
        } catch (e: Exception) {
            throw FailureResponse("DELETE_BLOG", e.message ?: "", e)
        }
    }

    private fun mapEntitiesToBlog(blogEntity: BlogEntity) =
        blogEntity.id?.let {
            Blog.fromEntity(blogEntity, contentRepository.findAllContentByBlogId(it).toContentList())
        } ?: throw NullFieldException("blog.id", BlogRepositoryAdapter::retrieveBlog.toString())
}
