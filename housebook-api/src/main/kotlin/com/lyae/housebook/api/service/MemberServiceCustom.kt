package com.lyae.housebook.api.service

import com.lyae.housebook.common.domain.Member
import com.lyae.housebook.common.repository.MemberRepository
import org.springframework.stereotype.Service

@Service
class MemberServiceCustom(
        val memberRepository: MemberRepository
) {
    fun singup(member: Member): Long {
        return memberRepository.save(member).id
    }
}