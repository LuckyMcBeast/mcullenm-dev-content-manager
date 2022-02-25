package dev.mcullenm.contentmanager

import org.apache.catalina.Context
import org.apache.tomcat.util.descriptor.web.SecurityCollection
import org.apache.tomcat.util.descriptor.web.SecurityConstraint
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
import org.springframework.boot.web.servlet.server.ServletWebServerFactory
import org.springframework.context.annotation.Bean

@SpringBootApplication
class ContentmanagerApplication

fun main(args: Array<String>) {
    runApplication<ContentmanagerApplication>(*args)

    @Bean
    fun servletContainer(): ServletWebServerFactory {
        return object : TomcatServletWebServerFactory() {
            override fun postProcessContext(context : Context)  {
                val securityConstraint = SecurityConstraint()
                securityConstraint.userConstraint = "CONFIDENTIAL"
                val collection = SecurityCollection();
                collection.addPattern("/*");
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
            }
        }
    }
}
