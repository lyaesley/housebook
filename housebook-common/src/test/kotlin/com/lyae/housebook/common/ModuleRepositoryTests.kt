package com.lyae.housebook.common

import com.lyae.housebook.common.domain.Member
import com.lyae.housebook.common.repository.MemberRepository
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.test.context.ContextConfiguration

@DataJpaTest
@EnableJpaRepositories(basePackages = arrayOf("com.lyae.housebook.common"))
class ModuleRepositoryTests {

    @MockBean
    lateinit var memberRepository: MemberRepository

    @Test
    fun add() {
        memberRepository.save(Member(name = "test", email = "jojoldu@gmail.com"))
        val findall = memberRepository.findAll()

        for(m in findall) {

            println("시작 /n $m /n 종료")
        }

    }
}