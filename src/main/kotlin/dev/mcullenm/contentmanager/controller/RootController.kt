package dev.mcullenm.contentmanager.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController("/")
class RootController {
    @GetMapping
    fun liveCheck() = "LIVE"
}