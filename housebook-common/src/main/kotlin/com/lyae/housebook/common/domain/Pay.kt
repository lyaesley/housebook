package com.lyae.housebook.common.domain

import javax.persistence.*

@Entity
data class Pay (
       @Id
       @GeneratedValue(strategy = GenerationType.AUTO)
       @Column(nullable = false)
       val payId: Long = 0,

       @ManyToOne(fetch = FetchType.LAZY)
       @JoinColumn(name = "memberId", nullable = false)
       var member: Member? = null,

//       @ManyToOne(fetch = FetchType.LAZY)
//       @JoinColumn(name = "ledgerId", nullable = false)
//       var financialLedger: FinancialLedger? = null,

){
       fun addMember(member: Member) {
              //기존 멤버와 관계를 제거
              this.member?.pays?.remove(this)

              this.member = member
              member.pays.add(this)
       }
}