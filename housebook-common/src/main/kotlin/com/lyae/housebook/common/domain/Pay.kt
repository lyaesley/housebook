package com.lyae.housebook.common.domain

import javax.persistence.*

@Entity
class Pay (
       @Id
       @GeneratedValue(strategy = GenerationType.AUTO)
       @Column(nullable = false)
       val payId: Long = 0,

       @ManyToOne(fetch = FetchType.LAZY)
       @JoinColumn(name = "memberId", nullable = false)
       var member: Member? = null,

       @ManyToOne(fetch = FetchType.LAZY)
       @JoinColumn(name = "ledgerId", nullable = false)
       var financialLedger: FinancialLedger? = null,

)