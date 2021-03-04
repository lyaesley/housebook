package com.lyae.housebook.common.repository

import com.lyae.housebook.common.domain.Member
import com.lyae.housebook.common.domain.Pay
import org.springframework.data.jpa.repository.JpaRepository

interface PayRepository : JpaRepository<Pay, Long> {

}
