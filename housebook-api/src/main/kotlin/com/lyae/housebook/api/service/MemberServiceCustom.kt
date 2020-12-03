package com.lyae.housebook.api.service

import com.lyae.housebook.common.domain.FinancialLedger
import com.lyae.housebook.common.domain.Member
import com.lyae.housebook.common.repository.MemberRepository
import org.springframework.stereotype.Service

@Service
class MemberServiceCustom(
        val memberRepository: MemberRepository,
) {
    fun signUp(member: Member): Member {
        val ledger = FinancialLedger(members = mutableListOf(member))
        member.financialLedger = ledger
        return memberRepository.save(member)
    }

    fun checkDuplication(member: Member): String? = when {
        memberRepository.existsByEmail(member.email) -> "이미 존재하는 이메일 입니다."
        memberRepository.existsByName(member.name) -> "이미 존재하는 이름 입니다."
        else -> null
    }

}