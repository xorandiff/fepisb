package com.xorandiff.fepisb.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class UsernameNotFoundException(message: String) : RuntimeException(message)