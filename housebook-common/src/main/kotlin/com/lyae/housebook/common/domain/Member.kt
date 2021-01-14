package com.lyae.housebook.common.domain

import javax.persistence.*

@Entity
data class Member(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(nullable = false)
        val memberId: Long = 0,

        @Column(nullable = true, length = 65, unique = true)
        val email: String = "",

        @Column(nullable = false, length = 512)
        val password: String = "",

        @Column(nullable = false, length = 16)
        val name: String = "",

        @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        @JoinColumn(name = "ledgerId", nullable = true)
        var financialLedger: FinancialLedger? = null,

        @OneToMany(mappedBy = "member")
        val pays: MutableList<Pay> = mutableListOf(),

        @OneToMany(mappedBy = "member")
        val category: MutableList<Category> = mutableListOf(),

//        @ManyToOne(fetch = FetchType.LAZY)
//        @JoinColumn(name = "ledgerId", nullable = true, insertable = false, updatable = false)
//        var guestLedger: FinancialLedger? = null
){
        fun addPay(pay: Pay) {
                pay.addMember(this)
        }

        fun addFinancialLedger(financialLedger: FinancialLedger) {
                //기존 관계를 제거
                this.financialLedger?.member?.remove(this)

                this.financialLedger = financialLedger
                financialLedger.member.add(this)
        }
}