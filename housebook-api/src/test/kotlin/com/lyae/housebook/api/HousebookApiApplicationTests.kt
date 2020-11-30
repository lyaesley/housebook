package com.lyae.housebook.api

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootTest
@EnableJpaRepositories(basePackages = arrayOf("com.lyae.housebook.common"))
class HousebookApiApplicationTests {

    @Test
    fun contextLoads() {
    }

}
