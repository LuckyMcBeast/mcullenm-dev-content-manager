package dev.mcullenm.contentmanager.repository

import dev.mcullenm.contentmanager.helper.createBlog
import dev.mcullenm.contentmanager.model.Blog
import dev.mcullenm.contentmanager.model.Content
import dev.mcullenm.contentmanager.model.response.CreateBlogResponse
import dev.mcullenm.contentmanager.model.response.DeleteBlogResponse
import dev.mcullenm.contentmanager.repository.jpa.BlogRepository
import dev.mcullenm.contentmanager.repository.jpa.ContentRepository
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import java.time.LocalDate

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
internal class BlogRepositoryAdapterTest {

    @Autowired
    private lateinit var entityManager: TestEntityManager

    private lateinit var blogRepositoryAdapter: BlogRepositoryAdapter

    @Autowired
    private lateinit var blogRepository: BlogRepository

    @Autowired
    private lateinit var contentRepository: ContentRepository

    private val publishDate: LocalDate =
        LocalDate.of(2021, 12, 9)

    @AfterAll
    @BeforeAll
    fun setup() {
        blogRepositoryAdapter = BlogRepositoryAdapter(blogRepository, contentRepository)
        blogRepository.deleteAll()
        contentRepository.deleteAll()
        blogRepository.resetSequence()
    }

    @Test
    fun `should retrieve blog`() {
        val content: List<Content> = listOf(Content(0, "p", "content goes here"))
        val blog = Blog(blogId = null, title = "My First Blog", publishDate = publishDate, content = content)
        val blogId = entityManager.createBlog(blog)
        val expected = blog.copy(blogId = blogId)

        val actual = blogRepositoryAdapter.retrieveBlog(blogId)

        assertThat(actual).isEqualTo(expected).usingRecursiveComparison()
    }

    @Test
    fun `should retrieve all blogs`() {
        val content: List<Content> = listOf(Content(0, "p", "content goes here"))
        val blog1 = Blog(blogId = null, title = "My First Blog", publishDate = publishDate, content = content)
        val blog2 = Blog(blogId = null, title = "My Second Blog", publishDate = publishDate, content = content)
        val (blogId1, blogId2) = Pair(entityManager.createBlog(blog1), entityManager.createBlog(blog2))
        val expected = listOf(blog1.copy(blogId = blogId1), blog2.copy(blogId = blogId2))

        val actual = blogRepositoryAdapter.retrieveBlogs()

        assertThat(actual).isEqualTo(expected).usingRecursiveComparison()
    }

    @Test
    fun `should save blog`() {
        val content: List<Content> = listOf(Content(0, "p", "content goes here"))
        val blog = Blog(blogId = null, title = "My First Blog", publishDate = publishDate, content = content)

        val actualResponse = blogRepositoryAdapter.createBlog(blog)
        val actualRetrieved = blogRepositoryAdapter.retrieveBlog(actualResponse.createdId)
        val expected = blog.copy(blogId = actualResponse.createdId)

        assertThat(actualResponse).isInstanceOf(CreateBlogResponse::class.java)
        assertThat(actualResponse.success).isTrue
        assertThat(actualResponse.contentAmount).isEqualTo(content.size)
        assertThat(actualRetrieved).isEqualTo(expected).usingRecursiveComparison()
    }

    @Test
    fun `should save blog with id if provided`() {
        val content: List<Content> = listOf(Content(0, "p", "content goes here"))
        val blog = Blog(blogId = 1000, title = "My First Blog", publishDate = publishDate, content = content)

        val actualResponse = blogRepositoryAdapter.createBlog(blog)
        val actualRetrieved = blogRepositoryAdapter.retrieveBlog(actualResponse.createdId)

        assertThat(actualResponse).isInstanceOf(CreateBlogResponse::class.java)
        assertThat(actualResponse.success).isTrue
        assertThat(actualResponse.contentAmount).isEqualTo(content.size)
        assertThat(actualRetrieved).isEqualTo(blog).usingRecursiveComparison()
    }

    @Test
    fun `should update blog`() {
        val content: List<Content> = listOf(Content(0, "p", "content goes here"))
        val blog = Blog(blogId = null, title = "My First Blog", publishDate = publishDate, content = content)
        val blogId = entityManager.createBlog(blog)
        val updatedBlog = blog.copy(title = "Not My First Blog")

        val actualResponse = blogRepositoryAdapter.updateBlog(blogId, updatedBlog)
        val actualRetrieved = blogRepositoryAdapter.retrieveBlog(blogId)
        val expected = updatedBlog.copy(blogId = actualResponse.createdId)

        assertThat(actualResponse).isInstanceOf(CreateBlogResponse::class.java)
        assertThat(actualResponse.success).isTrue
        assertThat(actualResponse.createdId).isEqualTo(blogId)
        assertThat(actualResponse.contentAmount).isEqualTo(content.size)
        assertThat(actualRetrieved).isEqualTo(expected).usingRecursiveComparison()
    }

    @Test
    fun `should return response with success = false when updating a blog that does not exist`() {
        val content: List<Content> = listOf(Content(0, "p", "content goes here"))
        val updatedBlog = Blog(blogId = null, title = "My First Blog", publishDate = publishDate, content = content)
        val blogId = 1000

        val actualResponse = blogRepositoryAdapter.updateBlog(blogId, updatedBlog)

        assertThat(actualResponse).isInstanceOf(CreateBlogResponse::class.java)
        assertThat(actualResponse.success).isFalse
        assertThat(actualResponse.createdId).isEqualTo(blogId)
        assertThat(actualResponse.contentAmount).isEqualTo(0)
    }

    @Test
    fun `should delete blog`() {
        val content: List<Content> = listOf(Content(0, "p", "content goes here"))
        val blog = Blog(blogId = null, title = "My First Blog", publishDate = publishDate, content = content)
        val blogId = entityManager.createBlog(blog)
        val expectedResponse = DeleteBlogResponse(true, blogId)

        val actualResponse = blogRepositoryAdapter.removeBlog(blogId)

        assertThat(actualResponse).isEqualTo(expectedResponse)
        assertThat(blogRepositoryAdapter.retrieveBlog(blogId)).isNull()
        assertThat(contentRepository.findAllContentByBlogId(blogId).size).isEqualTo(0)
    }
}
