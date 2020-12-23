package com.lyae.housebook.api

import com.lyae.housebook.api.dto.ResultStatus
import com.lyae.housebook.api.service.MemberServiceCustom
import com.lyae.housebook.common.domain.Member
import com.lyae.housebook.common.repository.MemberRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@DataJpaTest
@Import(MemberServiceCustom::class)
class MemberServiceTests(
        @Autowired val memberServiceCustom: MemberServiceCustom,
        @Autowired val memberRepository: MemberRepository,
) {
    @Test
    fun signUp() {
        val member = Member(name = "ljy", email = "ljy@ljy.com")
        println("저장 전 : $member")
        memberServiceCustom.signUp(member)
        val savedMember = memberRepository.findByIdOrNull(member.memberId)
        println("저장 후 : $member")

        assertEquals(savedMember?.memberId, member.memberId)
        assertEquals(savedMember?.name, member.name)
        assertEquals(savedMember?.email, member.email)
        assertNotNull(savedMember?.financialLedger)

        assertEquals(member, savedMember)
    }

    @Test
    fun checkDuplication() {
        //given
        val member = Member(name = "산군", email = "산군@naver.com")
        val dupNameMember = Member(name = "산군", email = "호랑이@naver.com")
        val dupEmailMember = Member(name = "빠르동생", email = "산군@naver.com")
        val availableMember = Member(name = "빠르", email = "빠르@naver.com")

        //when
        // 중복 체크후 중복이 아닐경우 member 저장
        memberServiceCustom.signUp(member)
        //then
        assertNotNull(member.financialLedger)

        // 중복 이메일 체크
        assertEquals(memberServiceCustom.signUp(dupEmailMember), ResultStatus("FAIL", "이미 존재하는 이메일 입니다."))
        // 중복 이름 체크
        assertEquals(memberServiceCustom.signUp(dupNameMember), ResultStatus("FAIL","이미 존재하는 이름 입니다."))

        // 중복아닌 member 체크
        memberServiceCustom.signUp(availableMember)

        assertNotNull(availableMember.financialLedger)

        println(availableMember)

        assertNotEquals(member.financialLedger, availableMember.financialLedger)
    }

    @Test
    fun abc() {

    }



}
