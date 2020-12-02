package com.lyae.housebook.common

import com.lyae.housebook.common.domain.FinancialLedger
import com.lyae.housebook.common.domain.Member
import com.lyae.housebook.common.repository.FinancialLedgerRepository
import com.lyae.housebook.common.repository.MemberRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@ExtendWith(SpringExtension::class)
@DataJpaTest
@ActiveProfiles("local")
// @Transactional 기능이 필요하지 않다면 아래와 같이한다. (테스트가 완료되도 자동으로 롤백하지 않는다)
 @Transactional(propagation = Propagation.NOT_SUPPORTED)
// AutoConfigureTestDatabase 어노테이션의 기본 설정값인 Replace.Any를 사용하면 기본적으로 내장된 임베디드 데이터베이스를 사용합니다.
// 위의 코드에서 Replace.NONE로 설정하면 @ActiveProfiles에 설정한 프로파일 환경값에 따라 데이터 소스가 적용됩니다.
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberAndFinancialLedgerTests(
        @Autowired val memberRepository: MemberRepository,
        @Autowired val financialLedgerRepository: FinancialLedgerRepository,
        @Autowired val em: TestEntityManager,
) {
    final val member = Member(
            email = "ljy@test.com",
            password = "1111",
            name = "이준영",
    )
    val ledger = FinancialLedger(members = mutableListOf(member))

    @BeforeEach
    fun setUp() {
        member.financialLedger = ledger
    }

    @Test
    fun createMemberAndLedger() {

        em.persist(member)

        println(member)
        println(ledger)

    }

    @Test
    fun save() {
        memberRepository.save(member)
        val find = memberRepository.findByIdOrNull(member.memberId)
        println(find)
    }
}