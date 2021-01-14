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

//        @OneToMany(mappedBy = "financialLedger")
//        val pay: MutableList<Pay> = mutableListOf(),

//        @OneToMany(mappedBy = "guestLedger")
//        val guestMembers: MutableList<Member> = mutableListOf(),

        @Column(nullable = false)
        val createDt: LocalDateTime = LocalDateTime.now().withNano(0),
) {
        override fun toString(): String {
                return "FinancialLedger(ledgerId=$ledgerId, createDt=$createDt)"
        }
}