package com.lyae.housebook.common

import com.lyae.housebook.common.domain.Member
import com.lyae.housebook.common.repository.MemberRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.junit.jupiter.SpringExtension


@ExtendWith(SpringExtension::class)
@DataJpaTest
class ModuleRepositoryTests(
        @Autowired val memberRepository: MemberRepository,
        @Autowired val entityManager: TestEntityManager,
) {
    @Test
    fun add() {
        val member = Member(name ="이준영", email = "test@test.com")
        memberRepository.save(member)

        val saved = memberRepository.findByIdOrNull(member.memberId)
        println(member)
        println(saved)
        assertEquals(member.name, saved?.name)
    }
}