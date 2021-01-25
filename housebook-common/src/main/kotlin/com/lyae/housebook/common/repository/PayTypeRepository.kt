package com.lyae.housebook.common.repository

import com.lyae.housebook.common.domain.PayType
import org.springframework.data.jpa.repository.JpaRepository

interface PayTypeRepository: JpaRepository<PayType, Long> {

}
