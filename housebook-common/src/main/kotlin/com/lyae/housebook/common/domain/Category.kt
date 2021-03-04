package com.lyae.housebook.common.domain

import javax.persistence.*

@Entity
data class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    val categoryId: Long = 0,

    @Column(nullable = false, length = 50)
    val name: String = "",

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId", nullable = false)
    var member: Member? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentId", nullable = true)
    var parent: Category? = null,   //카테고리 대

    @OneToMany(mappedBy = "parent")
    val children: MutableList<Category> = mutableListOf(),  //카테고리 소

    @OneToMany(mappedBy = "category")
    val pays: MutableList<Pay> = mutableListOf(),

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val payStatus: PayStatus = PayStatus.SPEND,   //결제분류 (수입:INCOME, 지출:SPEND, 이체:TRANSFER)

    ) {
    //==연관관계 메서드==//
    fun addChildCategory(child: Category) {
        this.children.add(child)
        child.parent = this
    }

    fun addParentCategory(parent: Category) {
        parent.children.add(this)
        this.parent = parent
    }

    fun addMember(member: Member) {
        this.member = member
        member.categories.add(this)
    }

    override fun toString(): String {
        return "Category(categoryId=$categoryId, name='$name', parent=$parent, payStatus=$payStatus)"
    }


}