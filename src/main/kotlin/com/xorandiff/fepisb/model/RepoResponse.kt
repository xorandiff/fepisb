package com.xorandiff.fepisb.model

data class RepoResponse(
    val name: String,
    val ownerLogin: String,
    val branches: List<Branch>
)

data class Branch(
    val name: String,
    val lastCommitSha: String
)