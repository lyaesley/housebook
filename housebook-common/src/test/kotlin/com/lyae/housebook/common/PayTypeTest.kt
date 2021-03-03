package com.lyae.housebook.common

import com.lyae.housebook.common.domain.Member
import com.lyae.housebook.common.domain.PayMethod
import com.lyae.housebook.common.domain.PayType
import com.lyae.housebook.common.repository.MemberRepository
import com.lyae.housebook.common.repository.PayTypeRepository
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class) //JUnit5 에서 사용함
@DataJpaTest
//static method가 없는 kotlin에서도 위와 같이 일반 함수에 @BeforeAll, @AfterAll 어노테이션을 사용할 수 있다.
//2021-01-25 @BeforeAll 에서 em 을 사용할 수 없었다.
//2021-03-03 @TestInstance(TestInstance.Lifecycle.PER_CLASS) 를 사용하면 em을 테스트클래스 마다 공유하여 주석처리후 @BeforeAll 을 @BeforeEach 로 대체함
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PayTypeTest(
    @Autowired val em: TestEntityManager,
    @Autowired val payTypeRepository: PayTypeRepository,
    @Autowired val memberRepository: MemberRepository,
) {
    private final val member준영 = Member(
        email = "leejy@test.com",
        name = "이준영",
        password = "1111",
    )

    private final val payType기본카드 = PayType(
        payMethod = PayMethod.CREDITCARD,
        name = "기본카드",
    )

    @BeforeEach
    fun beforeEach() {
        memberRepository.save(member준영)

        //결제수단 멤버 연결
        payType기본카드.addMember(member준영)

        //결제수단 저장
        payTypeRepository.save(payType기본카드)
    }

    @Test
    fun 결제종류_저장by멤버() {

        //given
        //결제수단 카드 생성
        val payType생성카드 = PayType(
            payMethod = PayMethod.ETC,
            name = "생성카드",
        ).apply { addMember(member준영) }

        //when

        //payType를 저장하기 전에 member 도 em에 관리되어 있어야 한다. beforeAll에서 저장
        //DB 저장
        payTypeRepository.save(payType생성카드)

        em.flush()
        em.detach(member준영)

        val findMember준영 = memberRepository.findByIdOrNull(member준영.memberId)!!

        //람다식 기본 문법
        //val findPayType = findMember준영.payTypes.find{ p ->  p.name == "생성카드"}
        //람다식 코틀린 it 문법
        val findPayType생성 = findMember준영.payTypes.find{ it.name == "생성카드"}
        val findPayType기본 = findMember준영.payTypes.find{ it.name == "기본카드"}

        //then
        Assertions.assertEquals(payType생성카드, findPayType생성)
        Assertions.assertEquals(payType기본카드.payTypeId, findPayType기본?.payTypeId)

    }

    @Test
    fun 결제종류_선택조회() {

        //given
        //when
        val find기본카드 = payTypeRepository.findByIdOrNull(payType기본카드.payTypeId)!!

        //then
//        Assertions.assertEquals(payType기본카드, find기본카드)
        Assertions.assertEquals(payType기본카드.payTypeId, find기본카드.payTypeId)
        Assertions.assertEquals(payType기본카드.name, find기본카드.name)
    }

    @Test
    fun 결제종류_전체조회byMember준영() {

        //given
        val payType생성통장 = PayType(
            payMethod = PayMethod.BANK,
            name = "생성통장",
        ).apply { addMember(member준영) }

        //when
        payTypeRepository.save(payType생성통장)

        //생성통장 DB 반영
        em.flush()

        //em.merge() 1차 캐시에서 entity 를 조회하고 없으면 DB에서 조회하고 1차 캐시에 저장한다.
        val find기본카드 = em.merge(payType기본카드)
        val findMember준영 = memberRepository.findByIdOrNull(member준영.memberId)!!

        //then
        Assertions.assertEquals(findMember준영.payTypes.size, 2)
        Assertions.assertTrue(findMember준영.payTypes.contains(find기본카드))
        Assertions.assertTrue(findMember준영.payTypes.contains(payType생성통장))

    }
}