package com.lyae.housebook.common.domain

import javax.persistence.*

@Entity
data class Member (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(nullable = false)
        val idx: Long = 0,

        @Column(nullable = true, length = 65, unique = true)
        val email: String = "",

        @Column(nullable = false, length = 512)
        var password: String = "",

        @Column(nullable = false, length = 16)
        val name: String = "",

)