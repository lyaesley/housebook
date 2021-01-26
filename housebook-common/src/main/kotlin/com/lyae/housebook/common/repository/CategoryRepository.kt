package com.lyae.housebook.common.repository

import com.lyae.housebook.common.domain.Category
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository: JpaRepository<Category, Long> {

}
