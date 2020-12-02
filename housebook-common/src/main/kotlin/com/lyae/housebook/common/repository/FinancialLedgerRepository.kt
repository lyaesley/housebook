package com.lyae.housebook.common.repository

import com.lyae.housebook.common.domain.FinancialLedger
import org.springframework.data.jpa.repository.JpaRepository

interface FinancialLedgerRepository: JpaRepository<FinancialLedger, Long> {
}