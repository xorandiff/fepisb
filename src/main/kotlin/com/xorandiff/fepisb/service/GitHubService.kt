package com.xorandiff.fepisb.service

import com.xorandiff.fepisb.exception.UsernameNotFoundException
import com.xorandiff.fepisb.model.Branch
import com.xorandiff.fepisb.model.RepoResponse
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate

@Service
class GitHubService(private val restTemplate: RestTemplate) {

    private val repoListType = object : ParameterizedTypeReference<List<Map<String, Any>>>() {}
    private val branchListType = object : ParameterizedTypeReference<List<Map<String, Any>>>() {}

    fun getRepos(username: String): List<RepoResponse> {
        val repos: List<Map<String, Any>> = try {
            restTemplate.exchange(
                "https://api.github.com/users/$username/repos",
                HttpMethod.GET,
                null,
                repoListType
            ).body ?: emptyList()
        } catch (ex: HttpClientErrorException.NotFound) {
            throw UsernameNotFoundException("GitHub user '$username' not found")
        }

        return repos
            .filter { it["fork"] as? Boolean == false }
            .map { repo ->
                val name = repo["name"] as String
                val ownerLogin = (repo["owner"] as Map<*, *>)["login"] as String
                val branches = fetchBranches(ownerLogin, name)
                RepoResponse(name, ownerLogin, branches)
            }
    }

    private fun fetchBranches(owner: String, repo: String): List<Branch> {
        val branches: List<Map<String, Any>> = restTemplate.exchange(
            "https://api.github.com/repos/$owner/$repo/branches",
            HttpMethod.GET,
            null,
            branchListType
        ).body ?: emptyList()

        return branches.map { b ->
            val branchName = b["name"] as String
            val sha = (b["commit"] as Map<*, *>)["sha"] as String
            
            Branch(branchName, sha)
        }
    }
}
