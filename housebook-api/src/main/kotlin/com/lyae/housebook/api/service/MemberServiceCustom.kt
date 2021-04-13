package com.lyae.housebook.api.service

import com.lyae.housebook.api.dto.ResultStatus
import com.lyae.housebook.common.domain.Category
import com.lyae.housebook.common.domain.FinancialLedger
import com.lyae.housebook.common.domain.Member
import com.lyae.housebook.common.repository.MemberRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class MemberServiceCustom(
        val memberRepository: MemberRepository,
) {
    fun signUp(member: Member): ResultStatus {
        checkDuplication(member)?.also { return@signUp it }
//        val ledger = FinancialLedger(member = mutableListOf(member))
//        member.financialLedger = ledger
        memberRepository.save(member)
        return ResultStatus("OK")
    }

    fun checkDuplication(member: Member): ResultStatus? = when {
        memberRepository.existsByEmail(member.email) -> ResultStatus("FAIL","이미 존재하는 이메일 입니다.")
        memberRepository.existsByName(member.name) -> ResultStatus("FAIL","이미 존재하는 이름 입니다.")
        else -> null
    }

    fun addCategory(member: Member, category: Category): ResultStatus {

        val findMember = memberRepository.findByIdOrNull(member.memberId)
        findMember?.let {
            category.addMember(it)
            ResultStatus("SUCCESS", "카테고리 저장 성공")
        }
        return ResultStatus("FAIL", "카테고리 저장 실패")

    }

}