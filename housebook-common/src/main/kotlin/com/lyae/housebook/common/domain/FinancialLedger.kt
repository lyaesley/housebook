package com.lyae.housebook.common.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class FinancialLedger(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(nullable = false)
        val teamId: Long = 0,

        @OneToMany(mappedBy = "financialLedger")
        val member: MutableList<Member> = mutableListOf(),

        @Column(nullable = false)
        val createDt: LocalDateTime = LocalDateTime.now().withNano(0),
) {
        override fun toString(): String {
                return "FinancialLedger(ledgerId=$teamId, createDt=$createDt)"
        }
}