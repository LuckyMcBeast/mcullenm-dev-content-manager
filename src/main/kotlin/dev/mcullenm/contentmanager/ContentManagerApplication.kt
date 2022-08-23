package dev.mcullenm.contentmanager

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ContentManagerApplication

fun main(args: Array<String>) {
    runApplication<ContentManagerApplication>(*args)
}
