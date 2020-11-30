package com.lyae.housebook.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

//@SpringBootApplication(scanBasePackages = arrayOf("com.lyae.housebook"))
@SpringBootApplication
@EnableJpaRepositories(basePackages = arrayOf("com.lyae.housebook.common"))
class HousebookApiApplication

fun main(args: Array<String>) {
    runApplication<HousebookApiApplication>(*args)
}
