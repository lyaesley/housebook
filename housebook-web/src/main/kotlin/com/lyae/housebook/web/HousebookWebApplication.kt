package com.lyae.housebook.web

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EntityScan(basePackages = arrayOf("com.lyae.housebook"))
@EnableJpaRepositories(basePackages = arrayOf("com.lyae.housebook"))
class HousebookWebApplication

fun main(args: Array<String>) {
    runApplication<HousebookWebApplication>(*args)
}
