package com.lyae.housebook.common

import com.lyae.housebook.common.domain.Member
import com.lyae.housebook.common.domain.Pay
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@DataJpaTest
class PayTests {

    @Test
    fun pay() {
        val pay = Pay()
        val member = Member(name ="이준영", email = "test@test.com")
        val changedMember = Member(name ="변경됨", email = "변경@test.com")

    }
}