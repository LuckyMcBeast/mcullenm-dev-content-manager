package dev.mcullenm.contentmanager

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ContentmanagerApplication

fun main(args: Array<String>) {
	runApplication<ContentmanagerApplication>(*args)
}
