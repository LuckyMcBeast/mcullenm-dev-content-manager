package dev.mcullenm.contentmanager.datasource

import dev.mcullenm.contentmanager.model.Blog
import dev.mcullenm.contentmanager.model.Content
import dev.mcullenm.contentmanager.model.request.CreateBlogRequest
import dev.mcullenm.contentmanager.model.response.CreateBlogResponse
import org.springframework.stereotype.Repository
import java.time.LocalDateTime.now

@Repository("Mock")
class MockBlogDataSource : BlogDataSource {

    final val loremIpsum1 = Content(0, "p", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque sed pretium turpis. Praesent rhoncus non lacus et molestie. Maecenas tempus dapibus massa, non imperdiet erat pulvinar quis. Sed ultrices ipsum ante, vel facilisis ligula blandit nec. Nam ut nunc neque. Ut rhoncus porttitor facilisis. Maecenas interdum pulvinar orci, ut faucibus erat mollis ultricies. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Nulla augue nisl, sagittis quis malesuada quis, varius ac augue. Nam congue lacinia tincidunt. Sed id pharetra enim, id malesuada velit. Maecenas quis nisi scelerisque, vestibulum magna ut, posuere risus. Aenean gravida et lorem at tempus. Etiam a lectus vitae dolor interdum commodo. Sed velit augue, cursus nec tristique non, euismod quis nibh.")
    final val loremIpsum2 = Content(1, "p", "Cras ac pharetra magna, sit amet mollis leo. Aenean magna nisl, egestas ut sagittis at, iaculis scelerisque ante. Maecenas in commodo turpis. Donec sollicitudin vel risus a semper. Cras vel dignissim dolor. Duis eget velit pharetra, lobortis ligula sit amet, sodales augue. Aenean nec gravida massa. Ut convallis nisi est, porttitor euismod urna hendrerit sit amet. Suspendisse faucibus ipsum eu felis eleifend suscipit. Donec consectetur quis diam id mattis. Vestibulum mauris diam, pulvinar egestas ligula sit amet, posuere lacinia arcu. Aenean euismod nisl nulla, a dapibus lacus varius eget. Cras et fermentum ex, eget rutrum quam. Duis risus odio, pharetra id tellus aliquam, sollicitudin pellentesque nunc.")
    final val loremIpsum3 = Content(2, "p", "Aliquam efficitur ipsum ac ultricies vulputate. Curabitur vehicula sed elit a eleifend. Vestibulum leo metus, cursus vitae risus ut, pulvinar tincidunt magna. Donec fermentum augue ut accumsan elementum. Cras et neque vitae eros pulvinar imperdiet vel ac elit. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nullam purus felis, gravida interdum tellus eget, efficitur convallis odio. Aliquam cursus nisi vitae sollicitudin eleifend.")
    final val loremIpsum4 = Content(3, "p", "Praesent eu massa eu libero gravida luctus eget in enim. Nullam nisi elit, mollis eget leo in, vulputate sollicitudin purus. Donec mattis massa massa, sit amet porttitor nibh fermentum sed. Aliquam facilisis lectus non rhoncus tempor. Quisque quis nunc fermentum, tempor dolor eu, imperdiet nulla. Suspendisse et feugiat nisl. Etiam lacinia finibus pulvinar.")
    final val loremIpsum5 = Content(4, "p", "Nullam nec metus nibh. Vestibulum id nisl ut sem ultrices ultricies sollicitudin sed massa. Nunc vel purus in felis egestas cursus. Nulla leo lectus, gravida vitae porta quis, ornare nec nulla. Nulla diam ex, consequat ut ligula at, pellentesque ullamcorper felis. Fusce eu ultricies eros. Quisque interdum velit nisi, ac hendrerit justo laoreet eget. Duis non aliquet lectus. Integer diam ipsum, consectetur vitae nisl ut, venenatis elementum ex. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Mauris mattis orci ut lacus mattis, a rhoncus metus maximus. Morbi lorem tellus, lobortis tincidunt elit id, faucibus tristique elit.")

    final val contentList = listOf(loremIpsum1, loremIpsum2, loremIpsum3, loremIpsum4, loremIpsum5)

    val blogs = mutableListOf(
        Blog(1, "My First Blog", "12-09-2021", contentList),
        Blog(2, "My Second Blog", "12-10-2021", contentList),
        Blog(3, "My Third Blog", "12-11-2021", contentList),
        Blog(4, "My Fourth Blog", "12-12-2021", contentList)
    )

    override fun retrieveBlogs(): Collection<Blog> {
        return blogs
    }

    override fun retrieveBlog(id: Int): Blog? {
        if (id <= 0) {
            return null
        }
        return blogs[id - 1]
    }

    override fun createBlog(createBlogRequest: CreateBlogRequest): CreateBlogResponse? {
        val blog = Blog(blogs.size + 1, createBlogRequest.title, now().toString(), createBlogRequest.content)
        blogs.add(blog)
        return CreateBlogResponse(true, blog.blogId, blog.content.size)
    }
}
