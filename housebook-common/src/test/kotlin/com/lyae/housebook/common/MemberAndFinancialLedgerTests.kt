package com.lyae.housebook.common

import com.lyae.housebook.common.domain.FinancialLedger
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
//@ActiveProfiles("local")
// @Transactional 기능이 필요하지 않다면 아래와 같이한다. (테스트가 완료되r도 자동으로 롤백하지 않는다)
// @Transactional(propagation = Propagation.NOT_SUPPORTED)
// AutoConfigureTestDatabase 어노테이션의 기본 설정값인 Replace.Any를 사용하면 기본적으로 내장된 임베디드 데이터베이스를 사용합니다.
// 위의 코드에서 Replace.NONE로 설정하면 @ActiveProfiles에 설정한 프로파일 환경값에 따라 데이터 소스가 적용됩니다.
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberAndFinancialLedgerTests(
        @Autowired val memberRepository: MemberRepository,
        @Autowired val em: TestEntityManager,
) {
    final val member = Member(
            email = "ljy@test.com",
            password = "1111",
            name = "이준영",
    ).also { it.financialLedger = FinancialLedger(member = it) }

//    @BeforeEach
//    fun setUp() {
//        member.financialLedger = ledger
//    }

    @Test
    fun createMemberAndLedger() {

        em.persist(member)
        println(member)
        em.detach(member)
        val ledgerOfMember = em.find(FinancialLedger::class.java, member.financialLedger?.ledgerId)
        println(ledgerOfMember.member)
        assertEquals(member.financialLedger?.ledgerId, ledgerOfMember.ledgerId)
        assertEquals(member.financialLedger?.createDt, ledgerOfMember.createDt)

    }

    @Test
    fun save() {
        //member DB 저장
        memberRepository.save(member)
        //entityManager (영속 컨텍스트) 에서 member 제거. 준영속 상태로 만듦
        em.detach(member)
        //DB에서 다시 조회
        val findMember = memberRepository.findByIdOrNull(member.memberId)


        assertEquals(member.memberId, findMember?.memberId)
        assertEquals(member.financialLedger?.ledgerId, findMember?.financialLedger?.ledgerId)
        assertEquals(member.financialLedger?.createDt, findMember?.financialLedger?.createDt)
    }
}