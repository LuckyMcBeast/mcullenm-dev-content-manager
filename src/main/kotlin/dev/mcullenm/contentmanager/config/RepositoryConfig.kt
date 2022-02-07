package dev.mcullenm.contentmanager.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.DefaultUriBuilderFactory

@Configuration
class RepositoryConfig {

    @Bean
    fun fastApiRestTemplate(@Value("\${fastapi.host}") fastApiHost: String): RestTemplate {
        val restTemplate = RestTemplate(HttpComponentsClientHttpRequestFactory())
        restTemplate.uriTemplateHandler = DefaultUriBuilderFactory(fastApiHost)
        return restTemplate
    }
}
