package com.lyae.housebook.common

import com.lyae.housebook.common.domain.Member
import com.lyae.housebook.common.repository.MemberRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@DataJpaTest
class MemberTest(
    @Autowired val memberRepository: MemberRepository,
    @Autowired val em: TestEntityManager,
) {
    private final val member준영 = Member(
        email = "ljy@test.com",
        name = "이준영",
        password = "1111",
    )

    @Test
    fun 멤버_저장and조회() {
        //member DB 저장
        memberRepository.save(member준영)

        //entityManager (영속 컨텍스트) 에서 member 제거. 준영속 상태로 만듦
        em.flush() //em 의 변경 내용을 데이터베이스에 반영한다.
        em.detach(member준영)

        //DB 에서 다시 조회
        val findMember = memberRepository.findByIdOrNull(member준영.memberId)!!

        Assertions.assertEquals(findMember.memberId, member준영.memberId)
        Assertions.assertEquals(findMember.email, "ljy@test.com")
        Assertions.assertEquals(findMember.name, "이준영")
        Assertions.assertEquals(findMember.password, "1111")
    }
}