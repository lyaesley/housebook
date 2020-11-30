package com.lyae.housebook.common.domain

import javax.persistence.*

@Entity
class Member (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(nullable = false)
        val id: Long = 0,

        @Column(nullable = false, length = 16)
        val name: String = "",

        @Column(nullable = true, length = 65, unique = true)
        val email: String = "",

)