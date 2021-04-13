package com.lyae.housebook.common.domain

import javax.persistence.*

@Entity
data class Pay (
       @Id
       @GeneratedValue(strategy = GenerationType.AUTO)
       @Column(nullable = false)
       val payId: Long = 0,

       @Column(nullable = false)
       val name: String = "",

       @Column(nullable = false)
       val price: Long = 0,

       @ManyToOne(fetch = FetchType.LAZY)
       @JoinColumn(name = "memberId", nullable = false)
       var member: Member? = null,

       @ManyToOne(fetch = FetchType.LAZY)
       @JoinColumn(name = "payMethodId", nullable = false)
       var payType: PayType? = null,  //결제수단 (사용자가 등록한 결제수단)

       @ManyToOne(fetch = FetchType.LAZY)
       @JoinColumn(name = "categoryId", nullable = false)
       var category: Category? = null,

       @Enumerated(EnumType.STRING)
       @Column(nullable = false)
       val payStatus: PayStatus? = null,  //결제분류 (수입:INCOME, 지출:SPEND, 이체:TRANSFER)

       @Embedded
       val period: Period

){
       fun addMember(member: Member) {
              //기존 관계를 제거
              this.member?.pays?.remove(this)

              this.member = member
              member.pays.add(this)
       }

       fun addPayMethod(payType: PayType) {
              //기존 관계를 제거
              this.payType?.pays?.remove(this)

              this.payType = payType
              payType.pays.add(this)
       }

       fun addCategory(category: Category) {
              //기존 관계를 제거
              this.category?.pays?.remove(this)

              this.category = category
              category.pays.add(this)
       }

       override fun toString(): String {
              return "Pay(payId=$payId, name='$name', payType=$payType, category=$category, payStatus=$payStatus)"
       }


}