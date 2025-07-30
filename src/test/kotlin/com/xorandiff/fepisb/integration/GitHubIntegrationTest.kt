package com.xorandiff.fepisb.integration

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class GitHubIntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `Happy path returns non-fork repos with branches`() {
        mockMvc.get("/github/octocat/repos")
            .andExpect {
                status { isOk() }
                jsonPath("$[0].name") { exists() }
                jsonPath("$[0].ownerLogin") { value("octocat") }
                jsonPath("$[0].branches") { isArray() }
                jsonPath("$[0].branches[0].lastCommitSha") { exists() }
            }
    }
}