package com.lyae.housebook.common

import com.lyae.housebook.common.domain.*
import com.lyae.housebook.common.repository.CategoryRepository
import com.lyae.housebook.common.repository.MemberRepository
import com.lyae.housebook.common.repository.PayRepository
import com.lyae.housebook.common.repository.PayTypeRepository
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
class PayTests(
    @Autowired val em: TestEntityManager,
    @Autowired val categoryRepository: CategoryRepository,
    @Autowired val memberRepository: MemberRepository,
    @Autowired val payTypeRepository: PayTypeRepository,
    @Autowired val payRepository: PayRepository,
) {

    private final val member준영 = Member(
        email = "leejy@test.com",
        name = "이준영",
        password = "1111",
    )

    private final val 식비카테고리 = Category(
        name = "식비",
        payStatus = PayStatus.SPEND,
    ).apply { addMember(member준영) }

    private final val 식비중식카테고리 = Category(
        name = "중식",
        payStatus = PayStatus.SPEND,
    ).apply {
        addMember(member준영)
        addParentCategory(식비카테고리)
    }

    private final val 쇼핑카테고리 = Category(
        name = "쇼핑",
        payStatus = PayStatus.SPEND,
    ).apply { addMember(member준영) }

    private final val 삼성카드결제 = PayType(
        name = "삼성카드",
        payMethod = PayMethod.CREDITCARD,
    ).apply { addMember(member준영) }

    private final val 카카오체크카드결제 = PayType(
        name = "카카오체크카드",
        payMethod = PayMethod.CHECKCARD,
    ).apply { addMember(member준영) }

    @BeforeEach
    fun beforeEach() {
        memberRepository.save(member준영)
        categoryRepository.save(식비카테고리)
        categoryRepository.save(식비중식카테고리)
        categoryRepository.save(쇼핑카테고리)
        payTypeRepository.save(삼성카드결제)
        payTypeRepository.save(카카오체크카드결제)
    }

    @Test
    fun 결제생성And결제조회By멤버() {
        //given
        val 점심결제 = Pay(
            name = "홍콩반점",
            price = 10000,
            payStatus = PayStatus.SPEND,
        ).apply {
            addMember(member준영)
            addCategory(식비중식카테고리)
            addPayMethod(삼성카드결제)
        }

        //when
        payRepository.save(점심결제)
        em.flush()
        em.clear()

        //then
        val find준영 = memberRepository.findByIdOrNull(member준영.memberId)!!
        val find점심결제 = find준영.pays.find { it.name == "홍콩반점" }

        Assertions.assertEquals(1, find준영.pays.size)
        Assertions.assertEquals(find준영.pays.find { it.name == "홍콩반점" }?.payId, 점심결제.payId)
        Assertions.assertEquals(find점심결제?.payId, 점심결제.payId)
        Assertions.assertEquals(find점심결제?.price, 10000)

    }

    @Test
    fun 결제조회검증() {

    }
}