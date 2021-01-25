package com.lyae.housebook.common.domain

import javax.persistence.*

@Entity
data class PayType(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    val payTypeId: Long = 0,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val payMethod: PayMethod? = null,    //결제수단종류(카드,현금,이체 등)

    @Column(nullable = false, length = 50)
    val name: String = "",

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId", nullable = false)
    var member: Member? = null,

    @OneToMany(mappedBy = "payType")
    val pays: MutableList<Pay> = mutableListOf()


) {
    fun addMember(member: Member) {
        this.member = member
        member.payTypes.add(this)
    }

    override fun toString(): String {
        return "PayType(payTypeId=$payTypeId, payMethod=$payMethod, name='$name')"
    }

}