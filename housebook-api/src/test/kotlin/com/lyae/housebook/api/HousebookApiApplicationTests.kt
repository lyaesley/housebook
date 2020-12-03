package com.lyae.housebook.api

import com.lyae.housebook.api.service.MemberServiceCustom
import com.lyae.housebook.common.domain.Member
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@DataJpaTest
@Import(MemberServiceCustom::class)
class HousebookApiApplicationTests(
        @Autowired val memberServiceCustom: MemberServiceCustom
) {
    @Test
    fun signUp() {
        val member = Member(name = "ljy", email = "ljy@ljy.com")
        println("저장 전 : $member")
        val savedMember: Member = memberServiceCustom.signUp(member)
        println("저장 후 : $member")
        assertEquals(savedMember.memberId, member.memberId)
        assertEquals(savedMember.name, member.name)
        assertEquals(savedMember.email, member.email)
        assertNotNull(savedMember.financialLedger)
    }

    @Test
    fun checkDuplication() {
        //given
        val member = Member(name = "산군", email = "산군@naver.com")
        val dupNameMember = Member(name = "산군", email = "호랑이@naver.com")
        val dupEmailMember = Member(name = "빠르동생", email = "산군@naver.com")
        val availableMember = Member(name = "빠르", email = "빠르@naver.com")
        //when
        assertNull(memberServiceCustom.checkDuplication(member))
        memberServiceCustom.signUp(member)
        //then
        assertNotNull(member.financialLedger)
        println(member)

        assertEquals(memberServiceCustom.checkDuplication(dupEmailMember), "이미 존재하는 이메일 입니다.")
        assertEquals(memberServiceCustom.checkDuplication(dupNameMember), "이미 존재하는 이름 입니다.")

        assertNull(memberServiceCustom.checkDuplication(availableMember))
        memberServiceCustom.signUp(availableMember)
        assertNotNull(availableMember.financialLedger)
        println(availableMember)

        assertNotEquals(member.financialLedger, availableMember.financialLedger)
    }




}
