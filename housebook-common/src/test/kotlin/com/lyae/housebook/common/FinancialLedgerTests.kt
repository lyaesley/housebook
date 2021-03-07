package com.lyae.housebook.common

import com.lyae.housebook.common.domain.FinancialLedger
import com.lyae.housebook.common.domain.Member
import com.lyae.housebook.common.repository.FinancialLedgerRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@DataJpaTest
class FinancialLedgerTests(
    @Autowired val em: TestEntityManager,
    @Autowired val ledger: FinancialLedgerRepository
) {

    private final val member준영 = Member(
        email = "test@test.com",
        name = "이준영",
    )

    private final val member친구 = Member(
        email = "friend@test.com",
        name = "친구",
    )

    @BeforeEach
    fun beforeEach() {
        em.persist(member준영)
        em.persist(member친구)
    }

    @Test
    fun 공유가계부연결(){

        //given
        val ledgerBy준영 = FinancialLedger().also {
            it.addMember(member준영)
            it.addMember(member친구)
        }

        em.persist(ledgerBy준영)
        em.flush()
        em.detach(ledgerBy준영)
        //when
        //then
        val findLedger준영 = ledger.findByIdOrNull(ledgerBy준영.ledgerId)!!

        Assertions.assertEquals(findLedger준영.member.size, 2)
        Assertions.assertNotNull(findLedger준영.member.find { it.memberId == member준영.memberId })
        Assertions.assertNotNull(findLedger준영.member.find { it.memberId == member친구.memberId })

    }

}