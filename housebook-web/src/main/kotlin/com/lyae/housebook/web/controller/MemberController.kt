package com.lyae.housebook.web.controller

import com.lyae.housebook.common.domain.Member
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MemberController {

    @GetMapping("/get")
    fun get(): Member = Member(name = "준영", email = "ljy@ljy.com")
}