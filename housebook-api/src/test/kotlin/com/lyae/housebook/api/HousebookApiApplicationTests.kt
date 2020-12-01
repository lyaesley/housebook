package com.lyae.housebook.api

import com.lyae.housebook.api.service.MemberServiceCustom
import com.lyae.housebook.common.domain.Member
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
class HousebookApiApplicationTests(
        @Autowired val memberServiceCustom: MemberServiceCustom
) {

    @Test
    fun save() {
        val member = Member(name = "ljy", email = "ljy@ljy.com")
        val idx: Long = memberServiceCustom.singup(member)
        assertEquals(idx, 1L)
    }

}
