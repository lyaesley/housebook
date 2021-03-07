package com.lyae.housebook.common.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class FinancialLedger(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(nullable = false)
        val ledgerId: Long = 0,

        @OneToMany(mappedBy = "financialLedger")
        val member: MutableList<Member> = mutableListOf(),

        @Column(nullable = false)
        val createDt: LocalDateTime = LocalDateTime.now().withNano(0),
) {
        override fun toString(): String {
                return "FinancialLedger(ledgerId=$ledgerId, createDt=$createDt)"
        }

        //연관관계 메소드
        fun addMember(member: Member) {
                this.member.add(member)
                member.financialLedger = this
        }
}