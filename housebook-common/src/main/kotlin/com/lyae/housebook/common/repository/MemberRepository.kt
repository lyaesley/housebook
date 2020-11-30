package com.lyae.housebook.common.repository

import com.lyae.housebook.common.domain.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {
    fun existsByName(name: String): Boolean
}

