package com.lyae.housebook.common.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class FinancialLedger(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(nullable = false)
        val ledgerId: Long = 0,

        @OneToMany(mappedBy = "financialLedger")
        val members: MutableList<Member> = mutableListOf(),

//        @OneToMany(mappedBy = "guestLedger")
//        val guestMembers: MutableList<Member> = mutableListOf(),

        @Column(nullable = false)
        val createDt: LocalDateTime = LocalDateTime.now(),
) {
        override fun toString(): String {
                return "FinancialLedger(ledgerId=$ledgerId, createDt=$createDt)"
        }
}