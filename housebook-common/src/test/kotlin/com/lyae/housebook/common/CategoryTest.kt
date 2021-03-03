package com.lyae.housebook.common

import com.lyae.housebook.common.domain.Category
import com.lyae.housebook.common.domain.Member
import com.lyae.housebook.common.domain.PayStatus
import com.lyae.housebook.common.repository.CategoryRepository
import com.lyae.housebook.common.repository.MemberRepository
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@DataJpaTest
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CategoryTest(
    @Autowired val em: TestEntityManager,
    @Autowired val categoryRepository: CategoryRepository,
    @Autowired val memberRepository: MemberRepository,
) {
    private final val member준영 = Member(
        email = "leejy@test.com",
        name = "이준영",
        password = "1111",
    )

    private final val category기본수입 = Category(
        name = "기본수입",
        payStatus = PayStatus.INCOME
    )
    private final val category기본지출 = Category(
        name = "기본지출",
        payStatus = PayStatus.SPEND
    )
    private final val category기본이체 = Category(
        name = "기본이체",
        payStatus = PayStatus.TRANSFER
    )

    @BeforeEach
    fun beforeEach() {
        memberRepository.save(member준영)

        //카테고리 멤버 연결
        category기본수입.addMember(member준영)
        category기본지출.addMember(member준영)
        category기본이체.addMember(member준영)

        //카테고리 저장
        categoryRepository.save(category기본수입)
        categoryRepository.save(category기본지출)
        categoryRepository.save(category기본이체)
    }

    @Test
    fun 카테고리_저장by멤버() {

        //given
        //카테고리 생성
        val category음식 = Category(
            name = "음식",
            payStatus = PayStatus.SPEND
        ).apply { addMember(member준영) }

        //when
        categoryRepository.save(category음식)

        em.flush()
        em.detach(member준영)

        //DB 에서 다시 조회
        val findMember준영 = memberRepository.findByIdOrNull(member준영.memberId)!!

        val findCategory음식 = findMember준영.categories.find { it.name == "음식" }

        //then
        Assertions.assertEquals(category음식, findCategory음식)

    }

    @Test
    fun 카테고리_전체조회byMember준영() {
        //given
        val category커피 = Category(
            name = "커피",
            payStatus = PayStatus.SPEND
        ).apply { addMember(member준영) }

        //when

        categoryRepository.save(category커피)
        //DB에 반영
        em.flush()

        val find기본수입 = em.merge(category기본수입)
        val findMember준영 = memberRepository.findByIdOrNull(member준영.memberId)!!

        //then
        Assertions.assertEquals(findMember준영.categories.size, 4)
        Assertions.assertTrue(findMember준영.categories.contains(find기본수입))
        Assertions.assertTrue(findMember준영.categories.contains(category커피))

    }

    @Test
    fun 카테고리_대소매핑() {
        // given
        val category지출식비대 = Category(
            name = "식비",
            payStatus = PayStatus.SPEND
        ).apply { addMember(member준영)}
        val category지출식비한식소 = Category(
            name = "한식",
            payStatus = PayStatus.SPEND
        ).apply { addMember(member준영)}
        val category지출식비중식소 = Category(
            name = "중식",
            payStatus = PayStatus.SPEND
        ).apply { addMember(member준영)}
        val category지출식비일식소 = Category(
            name = "일식",
            payStatus = PayStatus.SPEND
        ).apply { addMember(member준영)}
        val category지출식비양식소 = Category(
            name = "양식",
            payStatus = PayStatus.SPEND
        ).apply { addMember(member준영)}

        category지출식비대.addChildCategory(category지출식비한식소) //카테고리 대 소 매핑
        category지출식비대.addChildCategory(category지출식비중식소) //카테고리 대 소 매핑
        category지출식비대.addChildCategory(category지출식비일식소) //카테고리 대 소 매핑
        category지출식비대.addChildCategory(category지출식비양식소) //카테고리 대 소 매핑

        // when
        categoryRepository.save(category지출식비한식소)
        categoryRepository.save(category지출식비중식소)
        categoryRepository.save(category지출식비일식소)
        categoryRepository.save(category지출식비양식소)
        categoryRepository.save(category지출식비대)
        em.flush()
        em.detach(category지출식비대)

        val find수입식비대 = categoryRepository.findByName("식비")

        // then
        Assertions.assertNotNull(find수입식비대)
        Assertions.assertEquals(find수입식비대?.categoryId, category지출식비대.categoryId)
        Assertions.assertEquals(find수입식비대?.children?.size, 4)
        Assertions.assertEquals(find수입식비대?.children?.find{ it.name == "한식"}, category지출식비한식소)
        Assertions.assertEquals(find수입식비대?.children?.find{ it.name == "중식"}, category지출식비중식소)
        Assertions.assertEquals(find수입식비대?.children?.find{ it.name == "일식"}, category지출식비일식소)
        Assertions.assertEquals(find수입식비대?.children?.find{ it.name == "양식"}, category지출식비양식소)


    }






}