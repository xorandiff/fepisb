package com.xorandiff.fepisb.controller

import com.xorandiff.fepisb.exception.UsernameNotFoundException
import com.xorandiff.fepisb.service.GitHubService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/github")
class GitHubController(private val gitHubService: GitHubService) {

    @GetMapping("/{username}/repos")
    fun getUserRepos(@PathVariable username: String): ResponseEntity<Any> =
        try {
            val repos = gitHubService.getRepos(username)
            ResponseEntity.ok(repos)
        } catch (ex: UsernameNotFoundException) {
            val error = mapOf(
                "status" to HttpStatus.NOT_FOUND.value(),
                "message" to ex.message
            )
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(error)
        }
}